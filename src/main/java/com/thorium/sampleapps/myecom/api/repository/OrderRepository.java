package com.thorium.sampleapps.myecom.api.repository;

import com.thorium.sampleapps.myecom.api.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}
