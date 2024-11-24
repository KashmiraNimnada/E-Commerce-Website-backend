package com.ijse.springintro.repository;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ijse.springintro.entity.Order;

@ExtendWith(MockitoExtension.class)
public class OrderRepositoryTest {

    @Mock
    OrderRepository orderRepository;

    @Test
    void saveShouldSave() {

        Order order = new Order();
        order.setId(1L);
        order.setTotalPrice(100.0);

        when(orderRepository.save(order)).thenReturn(order);

        Order createdOrder = orderRepository.save(order);

        Assertions.assertEquals(order, createdOrder);
        Assertions.assertTrue(createdOrder.getTotalPrice()==100.0);
    }
    
    @Test
    void findByIdShouldfindById() {

        Order order = new Order();
        order.setId(1L);
        order.setTotalPrice(100.0);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Order gotOrder = orderRepository.findById(1L).orElse(null);

        Assertions.assertEquals(order, gotOrder);
        Assertions.assertTrue(gotOrder.getTotalPrice()==100.0);
    }

    @Test
    void findAllShouldFindAll() {

        Order order1 = new Order();
        order1.setId(1L);
        order1.setTotalPrice(100.0);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setTotalPrice(200.0);

        List<Order> orders = Arrays.asList(order1,order2);
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> order_list = orderRepository.findAll();

        Assertions.assertEquals(orders, order_list);
        Assertions.assertTrue(order_list.get(0).getTotalPrice()==100.0);
    }
}
