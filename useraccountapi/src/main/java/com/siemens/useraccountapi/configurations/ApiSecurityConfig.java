package com.siemens.useraccountapi.configurations;



import com.siemens.useraccountapi.filters.JWTAuthenticationFilter;
import com.siemens.useraccountapi.services.JWTUserAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
//@EnableWebSecurity
//spring boot 3 onwards
@EnableMethodSecurity
public class ApiSecurityConfig {

	@Autowired
	private JWTUserAuthService jwtUserAuthService;

	@Autowired
	private JWTAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private APIAuthenticationEntryPoint authenticationEntryPoint;


	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserAuthService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	 public WebSecurityCustomizer ignoringCustomizer() {
	        return (web) -> web.ignoring().requestMatchers( "/signup/v1.0","/signin/v1.0","/v3/api-docs/**",
					"/swagger-ui/**",
					"/swagger-ui.html","/swagger-ui/index.html");
	 }

	 @Bean
	  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authz -> authz
				 .requestMatchers("/v3/api-docs/**",
						 "/swagger-ui/**",
						 "/swagger-ui.html", "/signup/v1.0","/signin/v1.0","/swagger-ui/index.html")

				 
		 .permitAll().anyRequest()
			.authenticated())
		 
		 .exceptionHandling((exception)-> 
		 exception.authenticationEntryPoint(authenticationEntryPoint))
		 .sessionManagement(sess -> sess.sessionCreationPolicy
				 (SessionCreationPolicy.STATELESS))
		 
	     .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		      return http.build();
	   }


	@Bean
	public RegistrationBean jwtAuthFilterRegister(JWTAuthenticationFilter jwtAuthenticationFilter) {
		FilterRegistrationBean<JWTAuthenticationFilter> registrationBean = new FilterRegistrationBean<>(jwtAuthenticationFilter);
		registrationBean.setEnabled(false);
		return registrationBean;
	}

	 @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
         return authenticationConfiguration.getAuthenticationManager();
     }
 
	
		/*
		 * @Bean public AuthenticationManager authenticationManagerBean() throws
		 * Exception { return super.authenticationManagerBean(); }
		 */

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
