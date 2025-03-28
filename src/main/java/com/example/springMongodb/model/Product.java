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
