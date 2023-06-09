package com.ekart.ProductService.service;

import com.ekart.ProductService.model.ProductRequest;
import com.ekart.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productid, long quantity);
}
