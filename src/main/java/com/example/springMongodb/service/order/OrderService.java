package com.example.springMongodb.service.order;

import com.example.springMongodb.model.Order;
import java.util.List;

public interface OrderService {
    Long countOrders();
    Order createOrder(Order order);
    Order getOrderById(String id);
    List<Order> getAllOrders();
    List<Order> getOrdersByUser(String userId);
    List<Order> getOrdersByProduct(String productId);
    List<Order> getOrdersByStatus(String status);
    Order updateOrderStatus(String id, String status);
    Order deleteOrder(String id);
    Order updateOrder(String id, Order order);
    List<Order> getCartOrdersByUser(String userId);

    List<Order> confirmOrders(List<Order> order);
}