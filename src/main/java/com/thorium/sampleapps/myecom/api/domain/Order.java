package com.thorium.sampleapps.myecom.api.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;
import java.util.Objects;

@Document
public class Order extends ResourceSupport {

    @Id
    private String orderId;
    @DBRef
    private User buyer;
    @DBRef
    private List<Product> products;

    public Order() {
    }

    public Order(String idCommande, User acheteur, List<Product> produits) {
        this.orderId = idCommande;
        this.buyer = acheteur;
        this.products = produits;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
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
        if (!(o instanceof Order)) return false;
        Order commande = (Order) o;
        return Objects.equals(getOrderId(), commande.getOrderId()) &&
                Objects.equals(getBuyer(), commande.getBuyer()) &&
                Objects.equals(getProducts(), commande.getProducts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId(), getBuyer(), getProducts());
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", buyer=" + buyer +
                ", products=" + products +
                '}';
    }
}
