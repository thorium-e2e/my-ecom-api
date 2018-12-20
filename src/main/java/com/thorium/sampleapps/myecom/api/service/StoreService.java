package com.thorium.sampleapps.myecom.api.service;

import com.thorium.sampleapps.myecom.api.domain.Store;

import java.util.List;

public interface StoreService {

    Store saveStore(Store b);

    Store findById(String boutiqueId);

    void deleteById(String boutiqueId);

    void updateStore(Store b);

    boolean storeExists(Store b);

    List<Store> findAll();

    void deleteAll();
}
