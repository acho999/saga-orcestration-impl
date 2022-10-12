package com.angel.orderservice.saga.impl;

import com.angel.orderservice.services.impl.OrdersServiceImpl;
import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class SagaOrchestratorImplTest {

    @InjectMocks
    private SagaOrchestratorImpl orchestratorTest;
    @Mock private  SendMessage sendService;
    @Mock private  OrdersServiceImpl ordersService;
    @Mock private  Factory factory;

    @Test
    void handleOrderCreatedEvent() {
    }

    @Test
    void handleProductReservedEvent() {
    }

    @Test
    void handlePaymentProcessedEvent() {
    }

    @Test
    void handleOrderApprovedEvent() {
    }

    @Test
    void handleProductReservationCanceledEvent() {
    }

    @Test
    void handlePaymentCanceledEvent() {
    }

    @Test
    void handleOrderRejectedEvent() {
    }
}