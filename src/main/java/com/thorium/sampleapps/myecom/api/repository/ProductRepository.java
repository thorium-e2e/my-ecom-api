package com.thorium.sampleapps.myecom.api.repository;

import com.thorium.sampleapps.myecom.api.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
