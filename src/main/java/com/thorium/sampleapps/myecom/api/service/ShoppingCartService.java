package com.thorium.sampleapps.myecom.api.service;

import com.thorium.sampleapps.myecom.api.domain.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCart saveShoppingCart(ShoppingCart p);

    ShoppingCart findById(String id);

    void deleteById(String id);

    void updateShoppingCart(ShoppingCart p);

    boolean shoppingCartExists(ShoppingCart p);

    List<ShoppingCart> findAll();

    void deleteAll();
}
