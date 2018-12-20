package com.thorium.sampleapps.myecom.api.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;
import java.util.Objects;

@Document
public class ShoppingCart extends ResourceSupport {

    @Id
    private String shoppingCartId;
    @DBRef
    private User owner;
    @DBRef
    List<Product> products;

    public ShoppingCart() {
    }

    public ShoppingCart(String idPanier, User proprietaire, List<Product> produits) {
        this.shoppingCartId = idPanier;
        this.owner = proprietaire;
        this.products = produits;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingCart)) return false;
        ShoppingCart panier = (ShoppingCart) o;
        return Objects.equals(getShoppingCartId(), panier.getShoppingCartId()) &&
                Objects.equals(getOwner(), panier.getOwner()) &&
                Objects.equals(getProducts(), panier.getProducts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getShoppingCartId(), getOwner(), getProducts());
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "shoppingCartId='" + shoppingCartId + '\'' +
                ", owner=" + owner +
                ", products=" + products +
                '}';
    }
}
