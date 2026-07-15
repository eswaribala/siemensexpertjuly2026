package com.example.cqrs.external;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class DeliveryStatusClient {

    private final RestClient deliveryRestClient;

    public DeliveryStatusClient(
            RestClient deliveryRestClient
    ) {
        this.deliveryRestClient = deliveryRestClient;
    }

    public DeliveryStatusResponse getStatus(
            String referenceId
    ) {
        DeliveryStatusResponse response =
                deliveryRestClient
                        .get()
                        .uri("/delivery/status/{referenceId}",
                                referenceId)
                        .retrieve()
                        .body(DeliveryStatusResponse.class);

        if (response == null) {
            throw new IllegalStateException(
                    "Delivery API returned an empty response"
            );
        }

        return response;
    }
}