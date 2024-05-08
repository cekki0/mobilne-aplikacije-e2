package com.example.fijiapp.service;

import com.example.fijiapp.model.EventType;
import com.example.fijiapp.model.Product;
import com.example.fijiapp.repository.ProductRepository;
import com.google.android.gms.tasks.Task;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService() {
        productRepository = new ProductRepository();
    }

    public void updateProduct(Product product,String productId) {
        productRepository.updateProduct(product,productId);
    }

    public Task<Product> getProductById(String productId) {
        return productRepository.getProductById(productId);
    }
}
