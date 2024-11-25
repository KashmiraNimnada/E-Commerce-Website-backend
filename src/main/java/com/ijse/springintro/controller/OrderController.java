package com.ijse.springintro.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ijse.springintro.dto.OrderDTO;
import com.ijse.springintro.dto.OrderProductDto;
import com.ijse.springintro.entity.Order;
import com.ijse.springintro.entity.Product;
import com.ijse.springintro.service.OrderService;
import com.ijse.springintro.service.ProductService;

@RestController
@CrossOrigin (origins = "*")
public class OrderController {
    
    @Autowired 
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders(){
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.status(200).body(orders);
    }

    @GetMapping("orders/{id}")
    public ResponseEntity<Order> getOrderByID(@PathVariable Long id) {
        Order order = orderService.getOrderByID(id);
        if(order!=null) {
            return ResponseEntity.status(200).body(order);
        } else {
            return ResponseEntity.status(404).body(null);
        }
        
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO){
        Order order = new Order();
        order.setTotalPrice(0.0);
        List<Long> productIds = orderDTO.getProductIds();

        List<Product> productList=new ArrayList<>();

        for(Long productId : productIds) {
            Product product = productService.getProductById(productId);
            if(product!=null) {
                productList.add(product);
                order.setTotalPrice(order.getTotalPrice()+product.getPrice());
            }
        }

        order.setOrderedProducts(productList);
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(201).body(createdOrder);
    }

    @PostMapping("/orders/{id}/addProduct")
    public ResponseEntity<Order> addProductToOrder(@PathVariable Long id, @RequestBody OrderProductDto orderProductDto) {
        Order order = orderService.addProductToOrder(id,orderProductDto.getProductId(),orderProductDto.getQty());
        return ResponseEntity.status(201).body(order);
    }

    @DeleteMapping("/orders/{orderId}/product/{productId}")
    public ResponseEntity<Order> removeProductFromOrder(@PathVariable Long orderId,@PathVariable Long productId) {
        
        Order order = orderService.removeProductFromOrder(orderId, productId);
        return ResponseEntity.status(200).body(order);
    }

    @DeleteMapping("orders/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
 
}
