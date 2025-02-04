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

import static com.angel.models.constants.CommonConstants.FAKE_PRODUCT_ID;
import static com.angel.models.constants.CommonConstants.FAKE_USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


@DataJpaTest
@ActiveProfiles("test")
public class RepositoryTest {

    @Autowired
    private OrdersRepo repo;

    private String orderId;

    @BeforeEach
    void setUp() {
        Order order = Order.builder()
            .productId(FAKE_PRODUCT_ID)
            .qty(1)
            .orderState(OrderState.CREATED)
            .userId(FAKE_USER_ID)
            .build();
        this.repo.saveAndFlush(order);
        this.orderId = order.getOrderId();
    }

    @Test
    public void shouldCreateAndSaveOrderTest(){
        Order order = Order.builder()
            .productId(FAKE_PRODUCT_ID)
            .qty(1)
            .orderState(OrderState.CREATED)
            .userId(FAKE_USER_ID)
            .build();
        Order savedOrder = this.repo.saveAndFlush(order);
        assertFalse(savedOrder.getOrderId().isEmpty());
    }

    @Test
    public void shouldGetOrderByIdTest(){
        Order order = this.repo.getById(this.orderId);
        assertEquals(this.orderId, order.getOrderId());
    }

    @Test
    public void shouldUpdateOrderByIdTest(){
        Order orderUpdated = null;
        Optional<Order> order = this.repo.findById(this.orderId);
        if(order.isPresent()){
            order.get().setOrderState(OrderState.COMPLETED);
            //create order
            orderUpdated = this.repo.saveAndFlush(order.get());
        }
        if (orderUpdated != null) {
            assertEquals(OrderState.COMPLETED, orderUpdated.getOrderState());
            return;
        }
        fail();
    }

    @Test
    public void shouldGetAllOrdersTest(){
        List<Order> orders = this.repo.findAll();
        assertTrue(orders.size() > 0);
    }

    @Test
    public void shouldDeleteOrderByIdTest(){
        Optional<Order> order = this.repo.findById(this.orderId);
        order.ifPresent(value -> this.repo.delete(value));
        assertNull(this.repo.findById(this.orderId).orElse(null));
    }

}
