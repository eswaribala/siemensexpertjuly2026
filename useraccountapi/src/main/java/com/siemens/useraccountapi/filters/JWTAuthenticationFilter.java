package com.siemens.useraccountapi.filters;

import com.siemens.useraccountapi.exceptions.JwtTokenMissingException;
import com.siemens.useraccountapi.models.User;
import com.siemens.useraccountapi.services.JWTUserAuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.siemens.useraccountapi.configurations.JWTUtil;
import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    JWTUserAuthService jwtUserAuthService;
    private String token;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header=request.getHeader("Authorization");
        if((header==null)||(!header.startsWith("Bearer")))
            throw new JwtTokenMissingException("JWT Token not available in the request");
        else{

            token=header.substring(7);
            jwtUtil.validateToken(token);

          User user=  jwtUtil.getUserByToken(token);

            UserDetails userDetails=jwtUserAuthService.loadUserByUsername(user.getUserName());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

            filterChain.doFilter(request, response);

        }


    }
}
