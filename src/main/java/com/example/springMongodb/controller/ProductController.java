package com.example.springMongodb.controller;

import com.example.springMongodb.model.Product;
import com.example.springMongodb.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

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
        return ResponseEntity.ok(productService.getProductById(id));
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

    // New combined filter endpoint
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProducts(
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) List<String> colors,
            @RequestParam(required = false) List<String> sizes
    ) {
        return ResponseEntity.ok(productService.filterProducts(categories, colors, sizes));
    }

    // New endpoints to get all available options for filters
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        return ResponseEntity.ok(productService.getAllCategories());
    }

    @GetMapping("/colors")
    public ResponseEntity<List<String>> getAllColors() {
        return ResponseEntity.ok(productService.getAllColors());
    }

    @GetMapping("/sizes")
    public ResponseEntity<List<String>> getAllSizes() {
        return ResponseEntity.ok(productService.getAllSizes());
    }
}