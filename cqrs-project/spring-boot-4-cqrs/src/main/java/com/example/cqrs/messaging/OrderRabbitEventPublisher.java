package com.example.cqrs.messaging;

import com.example.cqrs.event.OrderCreatedEvent;
import com.example.cqrs.event.OrderStatusChangedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderRabbitEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public OrderRabbitEventPublisher(
            RabbitTemplate rabbitTemplate
    ) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void publishOrderCreated(
            OrderCreatedEvent event
    ) {
        rabbitTemplate.convertAndSend(
                RabbitMqNames.ORDER_EXCHANGE,
                RabbitMqNames.ORDER_CREATED_ROUTING_KEY,
                event,
                message -> {
                    message.getMessageProperties()
                            .setMessageId(
                                    event.eventId().toString()
                            );

                    message.getMessageProperties()
                            .setHeader(
                                    "eventType",
                                    "OrderCreatedEvent"
                            );

                    return message;
                }
        );
    }

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void publishOrderStatusChanged(
            OrderStatusChangedEvent event
    ) {
        rabbitTemplate.convertAndSend(
                RabbitMqNames.ORDER_EXCHANGE,
                RabbitMqNames.ORDER_STATUS_ROUTING_KEY,
                event,
                message -> {
                    message.getMessageProperties()
                            .setMessageId(
                                    event.eventId().toString()
                            );

                    message.getMessageProperties()
                            .setHeader(
                                    "eventType",
                                    "OrderStatusChangedEvent"
                            );

                    return message;
                }
        );
    }
}