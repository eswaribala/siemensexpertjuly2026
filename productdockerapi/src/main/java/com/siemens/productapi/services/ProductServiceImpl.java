package com.siemens.productapi.services;

import com.siemens.productapi.exceptions.ProductNameNotFoundException;
import com.siemens.productapi.exceptions.ProductNotFoundException;
import com.siemens.productapi.models.Product;
import com.siemens.productapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        if (product!=null)
          return productRepository.save(product);
        else
          throw new RuntimeException("Product is null");
    }

    @Override
    public Product updateProduct(long productId, double price) {
        Product product=productRepository.findById(productId).
                orElseThrow(()->new ProductNotFoundException("Product not found with id: "+productId));
        product.setPrice(price);
        return productRepository.save(product);
    }

    @Override
    public boolean deleteProduct(long productId) {
       boolean status=false;
       Product product=productRepository.findById(productId).
               orElseThrow(()->new ProductNotFoundException("Product not found with id: "+productId));

       productRepository.deleteById(productId);
       status=true;

       return status;

    }

    @Override
    public Product findProductById(long id) {
        return productRepository.findById(id).
                orElseThrow(()->new ProductNotFoundException("Product not found with id: "+id));
    }

    @Override
    public List<Product> findProductByName(String name) {
       List<Product> products= this.productRepository.findByProductName(name);
       if(products==null)
          throw new ProductNameNotFoundException("Product not found with name: "+name);
       else
           return products;

    }

    @Override
    public List<Product> findAllProducts() {
        return this.productRepository.findAll();
    }
}
