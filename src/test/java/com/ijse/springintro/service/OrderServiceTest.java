package com.ijse.springintro.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Arrays;
import com.ijse.springintro.entity.Order;
import com.ijse.springintro.entity.Product;
import com.ijse.springintro.repository.OrderRepository;
import com.ijse.springintro.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    
    @Mock
    ProductRepository productRepository;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderServiceImpl orderServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrderShouldCreateOrder() {

        Order order = new Order();
        order.setId(1L);
        order.setTotalPrice(6000.0);
        Mockito.when(orderRepository.save(order)).thenReturn(order);

        Order createdOrder = orderServiceImpl.createOrder(order);
        Assertions.assertTrue(createdOrder.getId()==1L);
        Assertions.assertTrue(createdOrder.getTotalPrice()==order.getTotalPrice());
    
    }
 
    @Test
    void getAllOrdersShouldGetAllOrders() {

        Order order1 = new Order();
        order1.setId(1L);
        order1.setTotalPrice(5000.0);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setTotalPrice(1000.0);

        List<Order> orders = Arrays.asList(order1,order2);
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> orderedList = orderServiceImpl.getAllOrders();

        Assertions.assertEquals(orders, orderedList);
        Assertions.assertTrue(orderedList.get(0).getTotalPrice()==5000.0);
    }

    @Test
    void getOrderByIDShouldGetOrderByID() {

        Order order = new Order();
        order.setId(1L);
        order.setTotalPrice(5000.0);
        
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order neededOrder = orderServiceImpl.getOrderByID(order.getId());

        Assertions.assertEquals(order, neededOrder);
        Assertions.assertTrue(neededOrder.getId()==1L);
    }

    @Test
    void addProductToOrderShouldAddProductToOrder() {
        
        Long orderId = 1L;
        Long productId = 1L;
        int qty = 2;

        Order order = new Order();
        order.setId(orderId);
        order.setTotalPrice(100.0);
        order.setOrderedProducts(new ArrayList<>());
    
        Product product = new Product();
        product.setId(productId);
        product.setPrice(50.0);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderServiceImpl.addProductToOrder(orderId, productId, qty);
        
        Assertions.assertTrue(result.getTotalPrice()==200.0);
    }

    @Test
    void removeProductFromOrderShouldRemoveProductFromOrder() {

        Product product1 = new Product();
        product1.setPrice(50.0);

        Order order = new Order();
        order.setTotalPrice(200.0);
        order.setId(1L);
        order.setOrderedProducts(new ArrayList<>());
        order.getOrderedProducts().add(product1);

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(productRepository.findById(product1.getId())).thenReturn(Optional.of(product1));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderServiceImpl.removeProductFromOrder(order.getId(), product1.getId());

        Assertions.assertTrue(result.getTotalPrice()==150.0);
    }

}
