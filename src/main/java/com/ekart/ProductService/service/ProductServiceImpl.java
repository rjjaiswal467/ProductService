package com.ekart.ProductService.service;

import com.ekart.ProductService.entity.Product;
import com.ekart.ProductService.exception.ProductServiceCustomException;
import com.ekart.ProductService.model.ProductRequest;
import com.ekart.ProductService.model.ProductResponse;
import com.ekart.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding product..");
        Product product = Product.builder()
                .productName(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
        productRepository.save(product);
        log.info("Product created");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Getting the product for productId: {}", productId);

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() -> new ProductServiceCustomException("Product not found", "PRODUCT_NOT_FOUND"));
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);

        return productResponse;
    }

    @Override
    public void reduceQuantity(long productid, long quantity) {
        log.info("Reduce quantity {} for id:{}", quantity, productid);
        Product product
                =productRepository.findById(productid)
                .orElseThrow(()->new ProductServiceCustomException(
                        "Product with given id not found",
                        "PRODUCT_NOT_FOUND"
                ));
        if(product.getQuantity()<quantity){
            throw new ProductServiceCustomException(
                    "Product does not have sufficient quantity",
                    "INSUFFICIENT_QUANTITY"
            );
        }

        product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);
        log.info("Product quantity updated successfully");
    }
}
