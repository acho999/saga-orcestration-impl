package com.angel.orderservice.endpoints;

import com.angel.orderservice.services.api.OrdersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrdersControllerTest {

    @InjectMocks
    private OrdersController controller;
    @Mock private OrdersService service;

    @Test
    void createOrder() {
    }

    @Test
    void getOrder() {
    }

    @Test
    void cancelOrder() {
    }

    @Test
    void approveOrder() {
    }
}