package com.example.cqrs.external;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.web.client.RestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryStatusClientTest {

    @RegisterExtension
    static WireMockExtension wireMock =
            WireMockExtension.newInstance()
                    .options(wireMockConfig().dynamicPort())
                    .build();

    @Test
    void shouldReturnDeliveryStatus() {

        wireMock.stubFor(
                get(urlEqualTo(
                        "/delivery/status/REF-1001"
                ))
                        .willReturn(
                                okJson("""
                                        {
                                          "referenceId": "REF-1001",
                                          "status": "DELIVERED",
                                          "message": "Package delivered"
                                        }
                                        """)
                        )
        );

        RestClient restClient =
                RestClient.builder()
                        .baseUrl(wireMock.baseUrl())
                        .build();

        DeliveryStatusClient client =
                new DeliveryStatusClient(restClient);

        DeliveryStatusResponse response =
                client.getStatus("REF-1001");

        assertEquals(
                "REF-1001",
                response.referenceId()
        );

        assertEquals(
                "DELIVERED",
                response.status()
        );

        wireMock.verify(
                1,
                getRequestedFor(
                        urlEqualTo(
                                "/delivery/status/REF-1001"
                        )
                )
        );
    }
}
