package com.example.cqrs.query.api;

import com.example.cqrs.command.model.OrderStatus;
import com.example.cqrs.query.model.OrderView;
import com.example.cqrs.query.service.OrderQueryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/queries/orders")
public class OrderQueryController {

    private final OrderQueryService queryService;

    public OrderQueryController(OrderQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/{orderId}")
    public OrderView findById(@PathVariable UUID orderId) {
        return queryService.findById(orderId);
    }

    @GetMapping
    public List<OrderView> findAll(
            @RequestParam(required = false) OrderStatus status) {
        return status == null
                ? queryService.findAll()
                : queryService.findByStatus(status);
    }
}
