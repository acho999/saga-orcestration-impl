package com.angel.orderservice.saga.impl;

import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class OrderSagaListenerImplTest {

    @InjectMocks
    private OrderSagaListenerImpl listenerTest;
    @Mock private  SendMessage sendService;
    @Mock private  Factory factory;

    @Test
    void handleApproveOrderCommand() {
    }

    @Test
    void handleRejectOrderCommandProduct() {
    }

    @Test
    void handleRejectOrderCommandPayment() {
    }
}