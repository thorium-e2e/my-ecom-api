package com.thorium.sampleapps.myecom.api.service.impl;

import com.thorium.sampleapps.myecom.api.domain.Store;
import com.thorium.sampleapps.myecom.api.repository.StoreRepository;
import com.thorium.sampleapps.myecom.api.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository boutiqueRepository;

    @Override
    public Store saveStore(Store b) {
        return boutiqueRepository.save(b);
    }

    @Override
    public Store findById(String boutiqueId) {
        return boutiqueRepository.findOne(boutiqueId);
    }

    @Override
    public void deleteById(String boutiqueId) {
        boutiqueRepository.delete(boutiqueId);
    }

    @Override
    public void updateStore(Store b) {
        boutiqueRepository.save(b);
    }

    @Override
    public boolean storeExists(Store b) {
        return boutiqueRepository.exists(Example.of(b));
    }

    @Override
    public List<Store> findAll() {
        return boutiqueRepository.findAll();
    }

    @Override
    public void deleteAll() {
        boutiqueRepository.deleteAll();
    }
}
