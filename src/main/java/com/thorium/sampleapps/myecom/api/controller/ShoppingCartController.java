package com.thorium.sampleapps.myecom.api.controller;

import com.thorium.sampleapps.myecom.api.domain.ShoppingCart;
import com.thorium.sampleapps.myecom.api.service.ShoppingCartService;
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
@RequestMapping("/shoppingCarts")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public HttpEntity<List<ShoppingCart>> getAllShoppingCarts() {
        List<ShoppingCart> shoppingCarts = shoppingCartService.findAll();
        if (shoppingCarts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            shoppingCarts.forEach(shoppingCart -> shoppingCart.add(linkTo(methodOn(ShoppingCartController.class).getAllShoppingCarts()).withRel("shoppingCarts")));
            shoppingCarts.forEach(shoppingCart -> shoppingCart.add(linkTo(methodOn(ShoppingCartController.class).getShoppingCartById(shoppingCart.getShoppingCartId())).withSelfRel()));
            return new ResponseEntity<>(shoppingCarts, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<ShoppingCart> getShoppingCartById(@PathVariable("id") String shoppingCartId) {
        ShoppingCart existingShoppingCart = shoppingCartService.findById(shoppingCartId);
        if (existingShoppingCart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            existingShoppingCart.add(linkTo(methodOn(ShoppingCartController.class).getShoppingCartById(existingShoppingCart.getShoppingCartId())).withSelfRel());
            return new ResponseEntity<>(existingShoppingCart, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public HttpEntity<?> saveShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        if (shoppingCartService.shoppingCartExists(shoppingCart)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            ShoppingCart newShoppingCart = shoppingCartService.saveShoppingCart(shoppingCart);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/shoppingCarts/{id}")
                    .buildAndExpand(newShoppingCart.getShoppingCartId()).toUri();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(location);
            return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public HttpEntity<?> updateShoppingCart(@PathVariable("id") String id, @RequestBody ShoppingCart shoppingCart) {
        ShoppingCart existingShoppingCart = shoppingCartService.findById(id);
        if(existingShoppingCart == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            existingShoppingCart.setOwner(shoppingCart.getOwner());
            existingShoppingCart.setProducts(shoppingCart.getProducts());
            shoppingCartService.updateShoppingCart(existingShoppingCart);
            existingShoppingCart.add(linkTo(methodOn(ShoppingCartController.class).getShoppingCartById(existingShoppingCart.getShoppingCartId())).withSelfRel());
            return new ResponseEntity<>(existingShoppingCart, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteShoppingCart(@PathVariable("id") String shoppingCartId) {
        shoppingCartService.deleteById(shoppingCartId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAll() {
        shoppingCartService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
