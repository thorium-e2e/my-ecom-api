package com.thorium.sampleapps.myecom.api.service.impl;

import com.thorium.sampleapps.myecom.api.domain.User;
import com.thorium.sampleapps.myecom.api.repository.UserRepository;
import com.thorium.sampleapps.myecom.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User saveUser(User u) {
        return repository.save(u);
    }

    @Override
    public User findById(String id) {
        return repository.findOne(id);
    }

    @Override
    public void deleteById(String id) {
        repository.delete(id);
    }

    @Override
    public void updateUser(User u) {
        repository.save(u);
    }

    @Override
    public boolean userExists(User u) {
        return repository.exists(Example.of(u));
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
