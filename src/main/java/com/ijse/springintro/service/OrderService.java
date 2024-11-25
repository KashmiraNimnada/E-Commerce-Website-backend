package com.ijse.springintro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ijse.springintro.entity.Order;

@Service
public interface OrderService {
    List<Order> getAllOrders();
    Order createOrder(Order order);
    Order getOrderByID(Long id);
    Order addProductToOrder(Long orderId,Long productId,int qty);
    Order removeProductFromOrder(Long orderId,Long productId);
    void deleteOrder(Long id);
}
