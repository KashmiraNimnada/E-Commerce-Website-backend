package com.ijse.springintro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijse.springintro.entity.Order;
import com.ijse.springintro.entity.Product;
import com.ijse.springintro.repository.OrderRepository;
import com.ijse.springintro.repository.ProductRepository;

@Service
public class OrderServiceImpl implements OrderService{
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderByID(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order addProductToOrder(Long orderId,Long productId,int qty) {
        
        Order order = orderRepository.findById(orderId).orElse(null);
        
        if(order==null){
            return null;
        }
        
        Product product = productRepository.findById(productId).orElse(null);
        
        if(product==null){
            return null;
        }

        order.getOrderedProducts().add(product);
        order.setTotalPrice(order.getTotalPrice()+(product.getPrice()*qty));

        return orderRepository.save(order);
    }

    @Override
    public Order removeProductFromOrder(Long orderId,Long productId){
        Order order = orderRepository.findById(orderId).orElse(null);

        if(order==null) {
            return null;
        }

        Product product = productRepository.findById(productId).orElse(null);
        if(product==null){
            return null;
        }

        order.getOrderedProducts().remove(product);
        order.setTotalPrice(order.getTotalPrice()-product.getPrice());

        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

}
