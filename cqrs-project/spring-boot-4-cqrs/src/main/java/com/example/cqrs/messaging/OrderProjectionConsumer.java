package com.example.cqrs.messaging;

import com.example.cqrs.event.OrderCreatedEvent;
import com.example.cqrs.event.OrderStatusChangedEvent;
import com.example.cqrs.query.model.OrderView;
import com.example.cqrs.query.repository.OrderQueryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderProjectionConsumer {

    private final OrderQueryRepository queryRepository;

    public OrderProjectionConsumer(
            OrderQueryRepository queryRepository
    ) {
        this.queryRepository = queryRepository;
    }

    @Transactional
    @RabbitListener(
            queues = RabbitMqNames.ORDER_CREATED_QUEUE
    )
    public void handleOrderCreated(
            OrderCreatedEvent event
    ) {
        /*
         * Basic idempotency:
         * if this order projection already exists,
         * do not insert it again.
         */
        if (queryRepository.existsById(event.orderId())) {
            return;
        }

        OrderView view = new OrderView(
                event.orderId(),
                event.customerName(),
                event.productName()
                        + " x "
                        + event.quantity(),
                event.quantity(),
                event.totalAmount(),
                event.status(),
                event.createdAt()
        );

        queryRepository.save(view);
    }

    @Transactional
    @RabbitListener(
            queues = RabbitMqNames.ORDER_STATUS_QUEUE
    )
    public void handleOrderStatusChanged(
            OrderStatusChangedEvent event
    ) {
        OrderView view = queryRepository
                .findById(event.orderId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Order projection not found: "
                                        + event.orderId()
                        )
                );

        /*
         * JPA dirty checking updates the read table
         * when the transaction commits.
         */
        view.changeStatus(event.status());
    }
}