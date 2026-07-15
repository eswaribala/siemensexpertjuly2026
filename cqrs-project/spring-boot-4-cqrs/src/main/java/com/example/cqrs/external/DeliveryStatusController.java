package com.example.cqrs.external;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "External API Demo",
        description = "WireMock demonstration without changing CQRS order processing"
)
@RestController
@RequestMapping("/api/external/delivery")
public class DeliveryStatusController {

    private final DeliveryStatusClient deliveryStatusClient;

    public DeliveryStatusController(
            DeliveryStatusClient deliveryStatusClient
    ) {
        this.deliveryStatusClient = deliveryStatusClient;
    }

    @Operation(
            summary = "Get mock delivery status",
            description = "Calls a WireMock API running on port 9090"
    )
    @GetMapping("/{referenceId}")
    public DeliveryStatusResponse getStatus(
            @PathVariable String referenceId
    ) {
        return deliveryStatusClient.getStatus(referenceId);
    }
}