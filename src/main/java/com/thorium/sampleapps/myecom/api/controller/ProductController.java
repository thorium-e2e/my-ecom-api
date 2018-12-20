package com.thorium.sampleapps.myecom.api.controller;

import com.thorium.sampleapps.myecom.api.domain.Product;
import com.thorium.sampleapps.myecom.api.service.CommentService;
import com.thorium.sampleapps.myecom.api.service.ProductService;
import com.thorium.sampleapps.myecom.api.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
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
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    private StoreService storeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public HttpEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            products.forEach(product -> product.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products")));
            products.forEach(product -> product.add(linkTo(methodOn(ProductController.class).getProductById(product.getProductId())).withSelfRel()));
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<Product> getProductById(@PathVariable("id") String productId) {
        Product existingProduct = productService.findById(productId);
        if (existingProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            existingProduct.add(linkTo(methodOn(ProductController.class).getProductById(existingProduct.getProductId())).withSelfRel());
            return new ResponseEntity<>(existingProduct, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public HttpEntity<?> saveProduct(@RequestBody Product product) {
        if (productService.productExist(product)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            Product newProduct = productService.saveProduct(product);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/products/{id}")
                    .buildAndExpand(newProduct.getProductId()).toUri();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(location);
            return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public HttpEntity<?> updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
        Product existingProduct = productService.findById(id);
        if(existingProduct == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            existingProduct.setComments(product.getComments());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setStore(product.getStore());
            productService.updateProduct(existingProduct);
            existingProduct.add(linkTo(methodOn(ProductController.class).getProductById(existingProduct.getProductId())).withSelfRel());
            return new ResponseEntity<>(existingProduct, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String productId) {
        productService.deleteById(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAll() {
        productService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> getProductsInStore(String storeId) {
        List<Product> products = storeService.findById(storeId).getProducts();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            products.forEach(product -> product.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products")));
            products.forEach(product -> product.add(linkTo(methodOn(ProductController.class).getProductById(product.getProductId())).withSelfRel()));
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }
}
