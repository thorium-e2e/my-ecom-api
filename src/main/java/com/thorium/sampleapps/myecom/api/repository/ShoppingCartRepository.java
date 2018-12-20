package com.thorium.sampleapps.myecom.api.repository;

import com.thorium.sampleapps.myecom.api.domain.ShoppingCart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShoppingCartRepository extends MongoRepository<ShoppingCart, String> {
}
