package com.cognizant.product.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClient;

import com.cognizant.product.dtos.CatalogResponse;
import com.cognizant.product.entities.Catalog;
import com.cognizant.product.mappers.CatalogMapper;
import com.cognizant.product.services.CatalogService;
import com.github.javafaker.Faker;

@WebMvcTest(CatalogController.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.cloud.vault.enabled=false",
        "spring.config.import=",
        "auditServiceUrl=http://localhost:9999/audit"
})
class CatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CatalogService catalogService;

    @MockitoBean
    private CatalogMapper catalogMapper;

    @MockitoBean
    private RestClient restClient;

    @MockitoBean
    private RestClient.RequestBodyUriSpec requestBodyUriSpec;

    @MockitoBean
    private RestClient.RequestBodySpec requestBodySpec;

    @MockitoBean
    private RestClient.ResponseSpec responseSpec;

    @Test
    void getAllCatalogsTest() throws Exception {
        List<Catalog> catalogList = getCatalogList();
        List<CatalogResponse> catalogResponseList = getCatalogResponseList(catalogList);

        when(catalogService.getAllCatalogs()).thenReturn(catalogList);
        when(catalogMapper.toCatalogResponseList(catalogList)).thenReturn(catalogResponseList);

        when(restClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toBodilessEntity()).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get("/catalogs/v1.0")
                .with(jwt().jwt(token -> token.claim("scope", "sre"))
                        .authorities(() -> "SCOPE_sre")))
                .andDo(print());
                //.andExpect(status().isOk())
                //.andExpect(jsonPath("$.data.length()").value(10));
    }

    private List<Catalog> getCatalogList() {
        List<Catalog> catalogList = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 0; i < 10; i++) {
            Catalog catalog = new Catalog();
            catalog.setCatalogId((long) faker.number().numberBetween(1, 100));
            catalog.setCatalogName(faker.commerce().productName());
            catalogList.add(catalog);
        }
        return catalogList;
    }

    private List<CatalogResponse> getCatalogResponseList(List<Catalog> catalogList) {
        List<CatalogResponse> responseList = new ArrayList<>();

        for (Catalog catalog : catalogList) {
            CatalogResponse response = new CatalogResponse(catalog.getCatalogId(), catalog.getCatalogName());
           
            responseList.add(response);
        }
        return responseList;
    }
}