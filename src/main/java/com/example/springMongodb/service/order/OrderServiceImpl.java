package com.example.springMongodb.service.order;

import com.example.springMongodb.model.Order;
import com.example.springMongodb.model.Product;
import com.example.springMongodb.model.Users;
import com.example.springMongodb.repository.OrderRepo;
import com.example.springMongodb.repository.ProductRepo;
import com.example.springMongodb.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepository;
    private final UserRepo userRepository;
    private final ProductRepo productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepository,
                            UserRepo userRepository,
                            ProductRepo productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Long countOrders() {
        return orderRepository.count();
    }

    @Override
    public Order createOrder(Order order) {
        // Validate user exists
        Users user = userRepository.findById(order.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate product exists
        Product product = productRepository.findById(order.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Set current date if not provided
        if (order.getDate() == null) {
            order.setDate(LocalDate.now());
        }

        // Set default status if not provided
        if (order.getStatus() == null || order.getStatus().isEmpty()) {
            order.setStatus("pending");
        }

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByUser(String userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> getOrdersByProduct(String productId) {
        return orderRepository.findByProductId(productId);
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public Order updateOrderStatus(String id, String status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        if ( order.getStatus().equals("completed") ){
            Product productPurchased = order.getProduct();
            productPurchased.setProductQuantity(productPurchased.getProductQuantity() - 1);
            productRepository.save(productPurchased);
            order.setProduct(productPurchased);
        }
        return orderRepository.save(order);
    }

    @Override
    public Order deleteOrder(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus("canceled");
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(String id, Order orderDetails) {
        Order existingOrder = getOrderById(id);

        if (orderDetails.getUser() != null) {
            Users user = userRepository.findById(orderDetails.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            existingOrder.setUser(user);
        }

        if (orderDetails.getProduct() != null) {
            Product product = productRepository.findById(orderDetails.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            existingOrder.setProduct(product);
        }

        if (orderDetails.getDate() != null) {
            existingOrder.setDate(orderDetails.getDate());
        }

        if (orderDetails.getPrice() != null) {
            existingOrder.setPrice(orderDetails.getPrice());
        }

        if (orderDetails.getStatus() != null && !orderDetails.getStatus().isEmpty()) {
            existingOrder.setStatus(orderDetails.getStatus());
        }

        return orderRepository.save(existingOrder);
    }

    @Override
    public List<Order> getCartOrdersByUser(String userId) {
        Users userSearched = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByStatusAndUser("pending", userSearched);
    }

    @Override
    public List<Order> confirmOrders(List<Order> orders) {
        return orders.stream()
                .map(order -> {
                    Product productOrder = order.getProduct();
                    productOrder.setProductQuantity(productOrder.getProductQuantity() - 1);
                    productRepository.save(productOrder);
                    order.setStatus("completed");
                    return orderRepository.save(order); // Assuming you have a repository
                })
                .collect(Collectors.toList());
    }
}