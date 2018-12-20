package com.thorium.sampleapps.myecom.api.service;

import com.thorium.sampleapps.myecom.api.domain.Order;

import java.util.List;

public interface OrderService {

    Order saveOrder(Order b);

    Order findById(String commandeId);

    void deleteById(String commandeId);

    void updateOrder(Order b);

    boolean orderExists(Order b);

    List<Order> findAll();

    void deleteAll();
}
