package com.cognizant.customerservice.facades;

import java.lang.annotation.Target;
import org.hibernate.annotations.IdGeneratorType;

import com.cognizant.customerservice.entities.AccountNoGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
@IdGeneratorType(AccountNoGenerator.class)
@Target({ ElementType.FIELD, ElementType.METHOD,
		ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface AccountNoAnnotator {

}
