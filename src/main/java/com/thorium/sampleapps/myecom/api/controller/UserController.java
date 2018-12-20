package com.thorium.sampleapps.myecom.api.controller;

import com.thorium.sampleapps.myecom.api.domain.User;
import com.thorium.sampleapps.myecom.api.service.StoreService;
import com.thorium.sampleapps.myecom.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    private StoreService storeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public HttpEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            users.forEach(user -> user.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users")));
            users.forEach(user -> user.add(linkTo(methodOn(UserController.class).getUserById(user.getUserId())).withSelfRel()));
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<User> getUserById(@PathVariable("id") String userId) {
        User existingUser = userService.findById(userId);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            existingUser.add(linkTo(methodOn(UserController.class).getUserById(existingUser.getUserId())).withSelfRel());
            return new ResponseEntity<>(existingUser, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public HttpEntity<?> saveUser(@RequestBody User user) {
        if (userService.userExists(user)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            User newUser = userService.saveUser(user);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/users/{id}")
                    .buildAndExpand(newUser.getUserId()).toUri();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(location);
            return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public HttpEntity<?> updateUser(@PathVariable("id") String id, @RequestBody User user) {
        User existingUser = userService.findById(id);
        if(existingUser == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            existingUser.setEmail(user.getEmail());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setShoppingCart(user.getShoppingCart());
            existingUser.setUserType(user.getUserType());
            userService.updateUser(existingUser);
            existingUser.add(linkTo(methodOn(UserController.class).getUserById(existingUser.getUserId())).withSelfRel());
            return new ResponseEntity<>(existingUser, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") String userId) {
        userService.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAll() {
        userService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> getUsersInShop(String storeId) {
        List<User> users = storeService.findById(storeId).getStaff();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            users.forEach(user -> user.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users")));
            users.forEach(user -> user.add(linkTo(methodOn(UserController.class).getUserById(user.getUserId())).withSelfRel()));
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }
}
