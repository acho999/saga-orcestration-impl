package com.angel.orderservice.services.impl;

import com.angel.orderservice.exceptions.NotFoundException;
import com.angel.orderservice.models.Order;
import com.angel.orderservice.repos.OrdersRepo;
import com.angel.saga.api.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrdersServiceImplTest {

    @InjectMocks
    private OrdersServiceImpl ordersServiceTest;
    @Mock private  OrdersRepo repo;
    @Mock private  SendMessage send;

    @Test
    void shouldGetOrderById() {
        String id = "fakeId";
        Order order = new Order();
        order.setOrderId(id);
        when(this.repo.findById(id)).thenReturn(Optional.of(order));
        assertEquals(id,this.ordersServiceTest.getOrder(id).getOrderId());

        order.setOrderId(null);
        IllegalArgumentException idNull = assertThrows(
            IllegalArgumentException.class,() -> this.ordersServiceTest.getOrder(order.getOrderId())
        );
        assertEquals("Id can not be null or empty string!", idNull.getMessage());

        order.setOrderId("");
        IllegalArgumentException idEmptyString = assertThrows(
            IllegalArgumentException.class,()->this.ordersServiceTest.getOrder(order.getOrderId())
        );
        assertEquals("Id can not be null or empty string!", idEmptyString.getMessage());

        when(this.repo.findById(id)).thenReturn(Optional.empty());
        order.setOrderId(id);
        NotFoundException notFoundException = assertThrows(
            NotFoundException.class, ()->this.ordersServiceTest.getOrder(order.getOrderId()));
        assertEquals("Order not found!", notFoundException.getMessage());
    }

    @Test
    void shouldCreateOrder() {
    }

    @Test
    void shouldThrowExceptionWhenCreateOrderWithOrderNull(){

    }

    @Test
    void shouldCancelOrder() {
    }

    @Test
    void shouldThrowExceptionWhenCancelOrderWithIdNullOrEmptyString(){

    }

    @Test
    void shouldThrowExceptionWhenOrderNotFoundForCanceling(){

    }

    @Test
    void shouldApproveOrder() {
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFoundForApproval(){

    }
}