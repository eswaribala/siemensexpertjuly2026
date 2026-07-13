package com.cognizant.customerservice.entities;

import java.util.EnumSet;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;

import com.github.javafaker.Faker;

public class AccountNoGenerator implements BeforeExecutionGenerator{

	@Override
	public EnumSet<EventType> getEventTypes() {
		// TODO Auto-generated method stub
		return EnumSet.of(EventType.INSERT);
	}

	@Override
	public Object generate(SharedSessionContractImplementor session, Object owner, Object currentValue,
			EventType eventType) {
		// TODO Auto-generated method stub
		Faker faker = new Faker();
		if(currentValue == null) {
			return Long.parseLong(faker.number().digits(12));
		}else
			return currentValue;
		
		
	}

}
