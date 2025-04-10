package com.example.springMongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    @DBRef
    private Users user;

    @DBRef
    private Product product;

    private LocalDate date;
    private Double price;
    private String status; // pending,cancelled, completed

    public Order() {
    }

    public Order(String status, Double price, LocalDate date, Product product, Users user) {
        this.status = status;
        this.price = price;
        this.date = date;
        this.product = product;
        this.user = user;
    }

    public Order(String id,
                 Users user,
                 Product product,
                 LocalDate date,
                 Double price,
                 String status) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.date = date;
        this.price = price;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", product=" + product +
                ", date=" + date +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
