package com.thorium.sampleapps.myecom.api.repository;

import com.thorium.sampleapps.myecom.api.domain.Store;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoreRepository extends MongoRepository<Store,String> {
}
