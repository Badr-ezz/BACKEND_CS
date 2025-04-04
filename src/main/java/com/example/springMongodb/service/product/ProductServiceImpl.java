package com.example.springMongodb.service.product;

import com.example.springMongodb.model.Product;
import com.example.springMongodb.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Long countProducts() {
        return productRepository.count();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findByProductCategory(List<String> categories) {
        return productRepository.findByProductCategoryIn(categories);
    }

    @Override
    public List<Product> findBySize(List<String> sizes) {
        return productRepository.findBySizeIn(sizes);
    }

    @Override
    public List<Product> findByColor(List<String> colors) {
        return productRepository.findByColorIn(colors);
    }

    @Override
    public List<Product> filterProducts(List<String> categories, List<String> colors, List<String> sizes) {
        // If no filters are applied, return all products
        if ((categories == null || categories.isEmpty()) &&
                (colors == null || colors.isEmpty()) &&
                (sizes == null || sizes.isEmpty())) {
            return getAllProducts();
        }

        // Start with all products
        List<Product> filteredProducts = getAllProducts();

        // Apply category filter if provided
        if (categories != null && !categories.isEmpty()) {
            filteredProducts = filteredProducts.stream()
                    .filter(product -> categories.contains(product.getProductCategory()))
                    .collect(Collectors.toList());
        }

        // Apply color filter if provided
        if (colors != null && !colors.isEmpty()) {
            filteredProducts = filteredProducts.stream()
                    .filter(product -> colors.contains(product.getColor()))
                    .collect(Collectors.toList());
        }

        // Apply size filter if provided
        if (sizes != null && !sizes.isEmpty()) {
            filteredProducts = filteredProducts.stream()
                    .filter(product -> {
                        // Assuming product.getSize() returns a String or you have a method to check
                        // Adjust this based on your Product model
                        return sizes.contains(product.getSize());
                    })
                    .collect(Collectors.toList());
        }

        return filteredProducts;
    }

    @Override
    public List<String> getAllCategories() {
        return getAllProducts().stream()
                .map(Product::getProductCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllColors() {
        return getAllProducts().stream()
                .map(Product::getColor)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllSizes() {
        return getAllProducts().stream()
                .map(Product::getSize)
                .distinct()
                .collect(Collectors.toList());
    }
}