package com.angel.orderservice.saga.impl;

import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static com.angel.models.constants.CommonConstants.COMMAND_CAN_NOT_BE_NULL;

@ExtendWith(MockitoExtension.class)
class OrderSagaListenerImplTest {

    @InjectMocks
    private OrderSagaListenerImpl listenerTest;
    @Mock private  SendMessage sendService;
    @Mock private  Factory factory;

    @Test
    void handleApproveOrderCommand() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,()->{this.listenerTest.handleApproveOrderCommand(null);}
        );
        assertEquals(COMMAND_CAN_NOT_BE_NULL, ex.getMessage());
    }

    @Test
    void handleRejectOrderCommandProduct() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,()->{this.listenerTest.handleRejectOrderCommandProduct(null);}
        );
        assertEquals(COMMAND_CAN_NOT_BE_NULL, ex.getMessage());
    }

    @Test
    void handleRejectOrderCommandPayment() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,()->{this.listenerTest.handleRejectOrderCommandPayment(null);}
        );
        assertEquals(COMMAND_CAN_NOT_BE_NULL, ex.getMessage());
    }
}