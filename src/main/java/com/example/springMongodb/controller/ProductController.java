package com.example.springMongodb.controller;

import com.example.springMongodb.model.Product;
import com.example.springMongodb.model.ProductSize;
import com.example.springMongodb.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/count")
    public ResponseEntity<Long> countProducts() {
        return ResponseEntity.ok(productService.countProducts());
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        // Fix: Handle case when product is not found
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.addProduct(product));
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // Add a combined filter endpoint to match frontend expectations
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProducts(
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) List<String> colors,
            @RequestParam(required = false) List<String> sizes
    ) {
        // Apply filters based on what parameters are provided
        if (categories != null && !categories.isEmpty()) {
            return ResponseEntity.ok(productService.findByProductCategory(categories));
        } else if (colors != null && !colors.isEmpty()) {
            return ResponseEntity.ok(productService.findByColor(colors));
        } else if (sizes != null && !sizes.isEmpty()) {
            return ResponseEntity.ok(productService.findBySize(sizes));
        }

        // If no filters provided, return all products
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // Keep the existing specific filter endpoints
    @GetMapping("/filter/category")
    public ResponseEntity<List<Product>> findByProductCategory(
            @RequestParam List<String> categories
    ) {
        return ResponseEntity.ok(productService.findByProductCategory(categories));
    }

    @GetMapping("/filter/size")
    public ResponseEntity<List<Product>> findBySize(
            @RequestParam List<String> size
    ) {
        return ResponseEntity.ok(productService.findBySize(size));
    }

    @GetMapping("/filter/color")
    public ResponseEntity<List<Product>> findByColor(
            @RequestParam List<String> colors
    ) {
        return ResponseEntity.ok(productService.findByColor(colors));
    }
}