package com.thorium.sampleapps.myecom.api.service;

import com.thorium.sampleapps.myecom.api.domain.Product;

import java.util.List;

public interface ProductService {

    Product saveProduct(Product p);

    Product findById(String id);

    void deleteById(String id);

    void updateProduct(Product p);

    boolean productExist(Product p);

    List<Product> findAll();

    void deleteAll();
}
