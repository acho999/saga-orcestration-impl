package com.angel.orderservice.repository;

import com.angel.models.states.OrderState;
import com.angel.orderservice.models.Order;
import com.angel.orderservice.repos.OrdersRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//@ActiveProfiles("test")
public class RepositoryTest {

    @Autowired
    private OrdersRepo repo;

    private String orderId;

    @BeforeEach
    void setUp() {
        Order order = Order.builder()
            .productId("shoes")
            .quantity(1)
            .state(OrderState.CREATED)
            .userId("userId")
            .build();
        this.repo.saveAndFlush(order);
        this.orderId = order.getOrderId();
    }

    @Test
    public void shouldCreateAndSaveOrderUnitTest(){
        Order order = Order.builder()
            .productId("shoes")
            .quantity(1)
            .state(OrderState.CREATED)
            .userId("userId")
            .build();
        Order savedOrder = this.repo.saveAndFlush(order);
        assertFalse(savedOrder.getOrderId().isEmpty());
    }

    @Test
    public void shouldGetOrderByIdUnitTest(){
        Order order = this.repo.getById(this.orderId);
        assertEquals(this.orderId, order.getOrderId());
    }

    @Test
    public void shouldUpdateOrderByIdUnitTest(){
        Order orderUpdated = null;
        Optional<Order> order = this.repo.findById(this.orderId);
        if(order.isPresent()){
            order.get().setState(OrderState.COMPLETED);
            orderUpdated = this.repo.saveAndFlush(order.get());
        }
        if (orderUpdated != null) {
            assertEquals(OrderState.COMPLETED, orderUpdated.getState());
            return;
        }
        fail();
    }

    @Test
    public void shouldGetAllOrdersUnitTest(){
        List<Order> orders = this.repo.findAll();
        assertTrue(orders.size() > 0);
    }

    @Test
    public void shouldDeleteOrderByIdUnitTest(){
        Optional<Order> order = this.repo.findById(this.orderId);
        order.ifPresent(value -> this.repo.delete(value));
        assertNull(this.repo.findById(this.orderId).orElse(null));
    }

}
