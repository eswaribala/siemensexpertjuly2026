package com.example.cqrs.command.api;

import com.example.cqrs.command.service.OrderCommandService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/commands/orders")
public class OrderCommandController {

    private final OrderCommandService commandService;

    public OrderCommandController(OrderCommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CreateOrderResponse create(@Valid @RequestBody CreateOrderCommand command) {
        UUID orderId = commandService.createOrder(command);
        return new CreateOrderResponse(orderId, "Order command accepted");
    }

    @PutMapping("/{orderId}/confirm")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void confirm(@PathVariable UUID orderId) {
        commandService.confirmOrder(orderId);
    }

    @PutMapping("/{orderId}/cancel")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void cancel(@PathVariable UUID orderId) {
        commandService.cancelOrder(orderId);
    }
}
