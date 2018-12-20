package com.thorium.sampleapps.myecom.api.service.impl;

import com.thorium.sampleapps.myecom.api.domain.Order;
import com.thorium.sampleapps.myecom.api.repository.OrderRepository;
import com.thorium.sampleapps.myecom.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository commandeRepository;

    @Override
    public Order saveOrder(Order b) {
        return commandeRepository.save(b);
    }

    @Override
    public Order findById(String commandeId) {
        return commandeRepository.findOne(commandeId);
    }

    @Override
    public void deleteById(String commandeId) {
        commandeRepository.delete(commandeId);
    }

    @Override
    public void updateOrder(Order b) {
        commandeRepository.save(b);
    }

    @Override
    public boolean orderExists(Order b) {
        return commandeRepository.exists(Example.of(b));
    }

    @Override
    public List<Order> findAll() {
        return commandeRepository.findAll();
    }

    @Override
    public void deleteAll() {
        commandeRepository.deleteAll();
    }
}
