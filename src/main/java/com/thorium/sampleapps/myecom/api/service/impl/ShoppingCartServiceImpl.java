package com.thorium.sampleapps.myecom.api.service.impl;

import com.thorium.sampleapps.myecom.api.domain.ShoppingCart;
import com.thorium.sampleapps.myecom.api.repository.ShoppingCartRepository;
import com.thorium.sampleapps.myecom.api.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository repository;

    @Override
    public ShoppingCart saveShoppingCart(ShoppingCart p) {
        return repository.save(p);
    }

    @Override
    public ShoppingCart findById(String id) {
        return repository.findOne(id);
    }

    @Override
    public void deleteById(String id) {
        repository.delete(id);
    }

    @Override
    public void updateShoppingCart(ShoppingCart p) {
        repository.save(p);
    }

    @Override
    public boolean shoppingCartExists(ShoppingCart p) {
        return repository.exists(Example.of(p));
    }

    @Override
    public List<ShoppingCart> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
