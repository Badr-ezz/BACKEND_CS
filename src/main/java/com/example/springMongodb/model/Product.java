package com.example.springMongodb.model;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String id;

    @NotBlank
    private String productName;

    public String getId() {
        return id;
    }

    public Product() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product(String id, String productName, String productCategory, long productPrice, int productQuantity, String color, String productImage, ProductSize size) {
        this.id = id;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.color = color;
        this.productImage = productImage;
        this.size = size;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(long productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public ProductSize getSize() {
        return size;
    }

    public void setSize(ProductSize size) {
        this.size = size;
    }

    @NotBlank
    private String productCategory;

    @NotBlank
    private  long productPrice;

    private int productQuantity;

    private String color;

    private String productImage;

    private ProductSize size;

    public Product(String productName, String productCategory, long productPrice, int productQuantity, String color, String productImage, ProductSize size) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.color = color;
        this.productImage = productImage;
        this.size = size;
    }
}
