package com.thorium.sampleapps.myecom.api.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;
import java.util.Objects;

@Document
public class Product extends ResourceSupport {

    @Id
    private String productId;
    @DBRef
    private Store store;
    @DBRef
    private List<Comment> comments;
    private String description;
    private int price;

    public Product() {
    }

    public Product(String idProduit, Store boutique, List<Comment> commentaires, String description, int prix) {
        this.productId = idProduit;
        this.store = boutique;
        this.comments = commentaires;
        this.description = description;
        this.price = prix;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        if (!super.equals(o)) return false;
        Product produit = (Product) o;
        return getPrice() == produit.getPrice() &&
                Objects.equals(getProductId(), produit.getProductId()) &&
                Objects.equals(getStore(), produit.getStore()) &&
                Objects.equals(getComments(), produit.getComments()) &&
                Objects.equals(getDescription(), produit.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductId(), getStore(), getComments(), getDescription(), getPrice());
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", store=" + store +
                ", comments=" + comments +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
