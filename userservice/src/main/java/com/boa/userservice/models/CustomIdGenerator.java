package com.boa.userservice.models;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;
import org.hibernate.generator.OnExecutionGenerator;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.UUID;

public class CustomIdGenerator implements BeforeExecutionGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object owner, Object currentValue, EventType eventType) {
        if(currentValue!=null){
            return currentValue;
        }else {
            return "user_"+UUID.randomUUID().toString().substring(0,8);
        }
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return EnumSet.of(EventType.INSERT);
    }
}
