package com.thorium.sampleapps.myecom.api.controller;

import com.thorium.sampleapps.myecom.api.domain.Store;
import com.thorium.sampleapps.myecom.api.service.StoreService;
import com.thorium.sampleapps.myecom.api.service.ProductService;
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
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public HttpEntity<List<Store>> getAllStores() {
        // trouver toutes les boutiques
        List<Store> stores = storeService.findAll();
        // si aucune boutique
        if (stores.isEmpty()) {
            // no content
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            // boutique(s) trouvées
            // ajout de liens hateoas
            // vers la liste des boutiques
            stores.forEach(store -> store.add(linkTo(methodOn(StoreController.class).getAllStores()).withRel("shops")));
            // vers cette boutique (self)
            stores.forEach(store -> store.add(linkTo(methodOn(StoreController.class).getStoreById(store.getStoreId())).withSelfRel()));
            // vers les produits
            stores.forEach(store -> store.getProducts().forEach(p -> {
                // tous les produits
                p.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
                // un produit en particulier
                p.add(linkTo(methodOn(ProductController.class).getProductById(p.getProductId())).withSelfRel());
            }));
            // vers le staff de la boutique
            stores.forEach(store -> store.getStaff().forEach(user -> {
                // tout le staff
                user.add(linkTo(methodOn(UserController.class).getUsersInShop(store.getStoreId())).withRel("staff"));
                // un membre particulier
                user.add(linkTo(methodOn(UserController.class).getUserById(user.getUserId())).withSelfRel());
            }));
            return new ResponseEntity<>(stores, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private HttpEntity<Store> getStoreById(@PathVariable("id") String storeId) {
        Store store = storeService.findById(storeId);
        if(store == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            store.add(linkTo(methodOn(StoreController.class).getAllStores()).withRel("shops"));
            store.add(linkTo(methodOn(StoreController.class).getStoreById(store.getStoreId())).withSelfRel());
            store.getStaff().forEach(user -> {
                user.add(linkTo(methodOn(UserController.class).getUsersInShop(storeId)).withRel("staff"));
                // un membre particulier
                user.add(linkTo(methodOn(UserController.class).getUserById(user.getUserId())).withSelfRel());
            });
            store.getProducts().forEach(product -> {
                // tous les produits
                product.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
                // un produit en particulier
                product.add(linkTo(methodOn(ProductController.class).getProductById(product.getProductId())).withSelfRel());
            });
            return new ResponseEntity<>(store, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public HttpEntity<?> saveStore(@RequestBody Store store) {
        if (storeService.storeExists(store)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            Store newStore = storeService.saveStore(store);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/stores/{id}")
                    .buildAndExpand(store.getStoreId()).toUri();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(location);
            return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public HttpEntity<?> updateStore(@PathVariable("id") String id, @RequestBody Store store) {
        Store existingStore = storeService.findById(id);
        if (existingStore == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            existingStore.setName(store.getName());
            existingStore.setDescription(store.getDescription());
            existingStore.setProducts(store.getProducts());
            existingStore.setStaff(store.getStaff());
            storeService.updateStore(existingStore);

            existingStore.add(linkTo(methodOn(StoreController.class).getAllStores()).withRel("stores"));
            existingStore.add(linkTo(methodOn(StoreController.class).getStoreById(existingStore.getStoreId())).withSelfRel());

            existingStore.getProducts().forEach(product -> {
                product.add(linkTo(methodOn(ProductController.class).getProductsInStore(existingStore.getStoreId())).withRel("products"));
                product.add(linkTo(methodOn(ProductController.class).getProductById(product.getProductId())).withSelfRel());
            });

            existingStore.getStaff().forEach(user -> {
                user.add(linkTo(methodOn(UserController.class).getUsersInShop(existingStore.getStoreId())).withRel("employees"));
            });
            return new ResponseEntity<>(storeService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStore(@PathVariable("id") String storeId) {
        storeService.deleteById(storeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAll() {
        storeService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
