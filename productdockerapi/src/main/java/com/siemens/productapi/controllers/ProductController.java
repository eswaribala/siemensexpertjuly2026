package com.siemens.productapi.controllers;

import com.siemens.productapi.dtos.GenericResponse;
import com.siemens.productapi.dtos.ProductRequest;
import com.siemens.productapi.dtos.ProductResponse;
import com.siemens.productapi.mappers.ProductMapper;
import com.siemens.productapi.models.Product;
import com.siemens.productapi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
     @Autowired
    private ProductService productService;
     @Autowired
     private ProductMapper productMapper;

     @PostMapping("/v1.0")


     ResponseEntity<GenericResponse<ProductResponse>> addProduct(@RequestBody ProductRequest productRequest) {
         //invoke the mapper
         Product product = productMapper.toEntity(productRequest);

         Product createdProduct = productService.addProduct(product);
         //entity to dto
         ProductResponse productResponse=productMapper.toResponse(createdProduct);

         return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<ProductResponse>(productResponse));
     }


    @GetMapping("/v1.0")


    ResponseEntity<GenericResponse<List<ProductResponse>>> getAllProducts() {


        List<Product> products = productService.findAllProducts();
        //entity to dto
        List<ProductResponse> productResponses=productMapper.toResponseList(products);

        return ResponseEntity.status(HttpStatus.OK).body(new
                GenericResponse<List<ProductResponse>>(productResponses));
    }

    @GetMapping("/v1.0/byId")


    ResponseEntity<GenericResponse<ProductResponse>> getProductById(@RequestParam long id) {


        Product product = productService.findProductById(id);
        //entity to dto
        ProductResponse productResponse=productMapper.toResponse(product);

        return ResponseEntity.status(HttpStatus.OK).body(new
                GenericResponse<ProductResponse>(productResponse));
    }

    @GetMapping("/v1.0/byName")


    ResponseEntity<GenericResponse<List<ProductResponse>>> getProductByName(@RequestParam String name) {


        List<Product> products = productService.findProductByName(name);
        //entity to dto
        List<ProductResponse> productResponses=productMapper.toResponseList(products);

        return ResponseEntity.status(HttpStatus.OK).body(new
                GenericResponse<List<ProductResponse>>(productResponses));
    }

    @PatchMapping("/v1.0")


    ResponseEntity<GenericResponse<ProductResponse>> updateProduct(@RequestParam long id, @RequestBody double price) {


        Product product= productService.updateProduct(id, price);
        //entity to dto
        ProductResponse productResponse=productMapper.toResponse(product);

        return ResponseEntity.status(HttpStatus.OK).body(new
                GenericResponse<ProductResponse>(productResponse));
    }
    @DeleteMapping("/v1.0")


    ResponseEntity<GenericResponse<String>> deleteProduct(@RequestParam long id) {


         if(productService.deleteProduct(id))

           return ResponseEntity.status(HttpStatus.OK).body(new
                GenericResponse<String>("Product with id "+id+" deleted successfully"));
         else
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new
                     GenericResponse<String>("Product with id "+id+" not found"));
    }

}
