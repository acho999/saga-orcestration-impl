package com.angel.orderservice.saga.impl;

import com.angel.orderservice.services.impl.OrdersServiceImpl;
import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.angel.models.constants.CommonConstants.EVENT_CAN_NOT_BE_NULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SagaOrchestratorImplTest {

    @InjectMocks
    private SagaOrchestratorImpl orchestratorTest;
    @Mock private  SendMessage sendService;
    @Mock private  OrdersServiceImpl ordersService;
    @Mock private  Factory factory;

    @Test
    void handleOrderCreatedEvent() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,()->{
                this.orchestratorTest.handleOrderCreatedEvent(null);
            }
        );
        assertEquals(EVENT_CAN_NOT_BE_NULL, ex.getMessage());
    }

    @Test
    void handleProductReservedEvent() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,()->{
                this.orchestratorTest.handleProductReservedEvent(null);
            }
        );
        assertEquals(EVENT_CAN_NOT_BE_NULL, ex.getMessage());
    }

    @Test
    void handlePaymentProcessedEvent() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,()->{
                this.orchestratorTest.handlePaymentProcessedEvent(null);
            }
        );
        assertEquals(EVENT_CAN_NOT_BE_NULL, ex.getMessage());
    }

    @Test
    void handleOrderApprovedEvent() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,()->{
                this.orchestratorTest.handleOrderApprovedEvent(null);
            }
        );
        assertEquals(EVENT_CAN_NOT_BE_NULL, ex.getMessage());
    }

    @Test
    void handleProductReservationCanceledEvent() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,()->{
                this.orchestratorTest.handleProductReservationCanceledEvent(null);
            }
        );
        assertEquals(EVENT_CAN_NOT_BE_NULL, ex.getMessage());
    }

    @Test
    void handlePaymentCanceledEvent() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,()->{
                this.orchestratorTest.handlePaymentCanceledEvent(null);
            }
        );
        assertEquals(EVENT_CAN_NOT_BE_NULL, ex.getMessage());
    }

    @Test
    void handleOrderRejectedEvent() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,()->{
                this.orchestratorTest.handleOrderRejectedEvent(null);
            }
        );
        assertEquals(EVENT_CAN_NOT_BE_NULL, ex.getMessage());
    }
}