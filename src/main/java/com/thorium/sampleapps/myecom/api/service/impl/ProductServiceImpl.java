package com.thorium.sampleapps.myecom.api.service.impl;

import com.thorium.sampleapps.myecom.api.domain.Product;
import com.thorium.sampleapps.myecom.api.repository.ProductRepository;
import com.thorium.sampleapps.myecom.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public Product saveProduct(Product p) {
        return repository.save(p);
    }

    @Override
    public Product findById(String id) {
        return repository.findOne(id);
    }

    @Override
    public void deleteById(String id) {
        repository.delete(id);
    }

    @Override
    public void updateProduct(Product p) {
        repository.save(p);
    }

    @Override
    public boolean productExist(Product p) {
        return repository.exists(Example.of(p));
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
