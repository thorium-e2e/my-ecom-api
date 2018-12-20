package com.thorium.sampleapps.myecom.api.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;
import java.util.Objects;

@Document
public class Store extends ResourceSupport {

    @Id
    private String storeId;
    private String name;
    private String description;
    @DBRef
    private List<User> staff;
    @DBRef
    private List<Product> products;

    public Store() {
    }

    public Store(String idBoutique, String name, String description, List<User> staff, List<Product> produits) {
        this.storeId = idBoutique;
        this.name = name;
        this.description = description;
        this.staff = staff;
        this.products = produits;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getStaff() {
        return staff;
    }

    public void setStaff(List<User> staff) {
        this.staff = staff;
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
        if (!(o instanceof Store)) return false;
        Store boutique = (Store) o;
        return Objects.equals(getStoreId(), boutique.getStoreId()) &&
                Objects.equals(getName(), boutique.getName()) &&
                Objects.equals(getDescription(), boutique.getDescription()) &&
                Objects.equals(getStaff(), boutique.getStaff()) &&
                Objects.equals(getProducts(), boutique.getProducts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStoreId(), getName(), getDescription(), getStaff(), getProducts());
    }

    @Override
    public String toString() {
        return "Store{" +
                "storeId='" + storeId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", staff=" + staff +
                ", products=" + products +
                '}';
    }


}
