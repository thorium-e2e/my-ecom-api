package com.thorium.sampleapps.myecom.api.domain;

import com.thorium.sampleapps.myecom.api.domain.enums.UserType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

import java.util.Objects;

@Document
public class User extends ResourceSupport {

    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private UserType userType;
    @DBRef
    private ShoppingCart shoppingCart;

    public User() {
    }

    public User(String idUtilisateur, String firstName, String lastName, String email, UserType typeUtilisateur, ShoppingCart panier) {
        this.userId = idUtilisateur;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userType = typeUtilisateur;
        this.shoppingCart = panier;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User that = (User) o;
        return Objects.equals(getUserId(), that.getUserId()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                getUserType() == that.getUserType() &&
                Objects.equals(getShoppingCart(), that.getShoppingCart());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getFirstName(), getLastName(), getEmail(), getUserType(), getShoppingCart());
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", userType=" + userType +
                ", shoppingCart=" + shoppingCart +
                '}';
    }
}
