/*
package com.example.cqrs.event;

import com.example.cqrs.query.model.OrderView;
import com.example.cqrs.query.repository.OrderQueryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderProjectionHandler {

    private final OrderQueryRepository queryRepository;

    public OrderProjectionHandler(OrderQueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @Async("projectionTaskExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void on(OrderCreatedEvent event) {
        OrderView view = new OrderView(
                event.orderId(),
                event.customerName(),
                event.productName() + " x " + event.quantity(),
                event.quantity(),
                event.totalAmount(),
                event.status(),
                event.createdAt()
        );
        queryRepository.save(view);
    }

    @Async("projectionTaskExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void on(OrderStatusChangedEvent event) {
        OrderView view = queryRepository.findById(event.orderId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order projection not found: " + event.orderId()
                ));
        view.changeStatus(event.status());
    }
}
*/
