package com.example.cqrs.config;


import com.example.cqrs.messaging.RabbitMqNames;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    DirectExchange orderExchange() {
        return new DirectExchange(
                RabbitMqNames.ORDER_EXCHANGE,
                true,
                false
        );
    }

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(
                RabbitMqNames.DEAD_LETTER_EXCHANGE,
                true,
                false
        );
    }

    @Bean
    Queue orderCreatedQueue() {
        return QueueBuilder
                .durable(RabbitMqNames.ORDER_CREATED_QUEUE)
                .deadLetterExchange(
                        RabbitMqNames.DEAD_LETTER_EXCHANGE
                )
                .deadLetterRoutingKey(
                        RabbitMqNames.ORDER_CREATED_DEAD_ROUTING_KEY
                )
                .build();
    }

    @Bean
    Queue orderStatusQueue() {
        return QueueBuilder
                .durable(RabbitMqNames.ORDER_STATUS_QUEUE)
                .deadLetterExchange(
                        RabbitMqNames.DEAD_LETTER_EXCHANGE
                )
                .deadLetterRoutingKey(
                        RabbitMqNames.ORDER_STATUS_DEAD_ROUTING_KEY
                )
                .build();
    }

    @Bean
    Queue orderCreatedDeadLetterQueue() {
        return QueueBuilder
                .durable(RabbitMqNames.ORDER_CREATED_DLQ)
                .build();
    }

    @Bean
    Queue orderStatusDeadLetterQueue() {
        return QueueBuilder
                .durable(RabbitMqNames.ORDER_STATUS_DLQ)
                .build();
    }

    @Bean
    Binding orderCreatedBinding(
            Queue orderCreatedQueue,
            DirectExchange orderExchange
    ) {
        return BindingBuilder
                .bind(orderCreatedQueue)
                .to(orderExchange)
                .with(RabbitMqNames.ORDER_CREATED_ROUTING_KEY);
    }

    @Bean
    Binding orderStatusBinding(
            Queue orderStatusQueue,
            DirectExchange orderExchange
    ) {
        return BindingBuilder
                .bind(orderStatusQueue)
                .to(orderExchange)
                .with(RabbitMqNames.ORDER_STATUS_ROUTING_KEY);
    }

    @Bean
    Binding orderCreatedDeadLetterBinding(
            Queue orderCreatedDeadLetterQueue,
            DirectExchange deadLetterExchange
    ) {
        return BindingBuilder
                .bind(orderCreatedDeadLetterQueue)
                .to(deadLetterExchange)
                .with(
                        RabbitMqNames.ORDER_CREATED_DEAD_ROUTING_KEY
                );
    }

    @Bean
    Binding orderStatusDeadLetterBinding(
            Queue orderStatusDeadLetterQueue,
            DirectExchange deadLetterExchange
    ) {
        return BindingBuilder
                .bind(orderStatusDeadLetterQueue)
                .to(deadLetterExchange)
                .with(
                        RabbitMqNames.ORDER_STATUS_DEAD_ROUTING_KEY
                );
    }

    @Bean
    JacksonJsonMessageConverter rabbitMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
