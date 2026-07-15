package com.example.cqrs.messaging;


public final class RabbitMqNames {

    private RabbitMqNames() {
    }

    public static final String ORDER_EXCHANGE =
            "cqrs.order.exchange";

    public static final String ORDER_CREATED_QUEUE =
            "cqrs.order.created.queue";

    public static final String ORDER_STATUS_QUEUE =
            "cqrs.order.status.queue";

    public static final String ORDER_CREATED_DLQ =
            "cqrs.order.created.dlq";

    public static final String ORDER_STATUS_DLQ =
            "cqrs.order.status.dlq";

    public static final String DEAD_LETTER_EXCHANGE =
            "cqrs.order.dlx";

    public static final String ORDER_CREATED_ROUTING_KEY =
            "order.created";

    public static final String ORDER_STATUS_ROUTING_KEY =
            "order.status.changed";

    public static final String ORDER_CREATED_DEAD_ROUTING_KEY =
            "order.created.dead";

    public static final String ORDER_STATUS_DEAD_ROUTING_KEY =
            "order.status.changed.dead";
}
