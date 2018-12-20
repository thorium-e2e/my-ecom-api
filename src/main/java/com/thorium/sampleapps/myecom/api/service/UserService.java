package com.thorium.sampleapps.myecom.api.service;

import com.thorium.sampleapps.myecom.api.domain.User;

import java.util.List;

public interface UserService {

    User saveUser(User u);

    User findById(String id);

    void deleteById(String id);

    void updateUser(User u);

    boolean userExists(User u);

    List<User> findAll();

    void deleteAll();
}
