package com.angel.orderservice.saga.impl;

import com.angel.models.commands.ApproveOrderCommand;
import com.angel.models.commands.RejectOrderCommandPayment;
import com.angel.models.commands.RejectOrderCommandProduct;
import com.angel.models.events.OrderApprovedEvent;
import com.angel.models.events.OrderRejectedEvent;
import com.angel.orderservice.services.api.ValidationService;
import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.angel.models.constants.CommonConstants.COMMAND_CAN_NOT_BE_NULL;
import static com.angel.models.constants.CommonConstants.FAKE_ORDER_ID;
import static com.angel.models.constants.CommonConstants.FAKE_PRODUCT_ID;
import static com.angel.models.constants.CommonConstants.FAKE_USER_ID;
import static com.angel.models.constants.CommonConstants.PAYMENT_ID;
import static com.angel.models.constants.CommonConstants.QUANTITY;
import static com.angel.models.constants.CommonConstants.REASON;
import static com.angel.models.constants.TopicConstants.APPROVE_ORDER_COMMAND;
import static com.angel.models.constants.TopicConstants.ORDER_APPROVED_EVENT;
import static com.angel.models.constants.TopicConstants.ORDER_REJECTED_EVENT;
import static com.angel.models.constants.TopicConstants.REJECT_ORDER_COMMAND_PAYMENT;
import static com.angel.models.constants.TopicConstants.REJECT_ORDER_COMMAND_PRODUCT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderSagaListenerImplTest {

    @InjectMocks
    private OrderSagaListenerImpl listenerTest;
    @Mock private  SendMessage sendService;
    @Mock private  Factory factory;
    @Mock private  ValidationService validationService;

    @Test
    void handleApproveOrderCommand() {
        ApproveOrderCommand cmnd = ApproveOrderCommand.builder()
            .orderId(FAKE_ORDER_ID)
            .productId(FAKE_PRODUCT_ID)
            .quantity(QUANTITY)
            .userId(FAKE_USER_ID)
            .build();
        OrderApprovedEvent event = OrderApprovedEvent.builder()
            .orderId(FAKE_ORDER_ID)
            .productId(FAKE_PRODUCT_ID)
            .quantity(QUANTITY)
            .userId(FAKE_USER_ID)
            .build();
        when(this.factory.readCommand(APPROVE_ORDER_COMMAND,ORDER_APPROVED_EVENT,cmnd))
            .thenReturn(event);
        assertEquals(FAKE_ORDER_ID, this.listenerTest.handleApproveOrderCommand(cmnd).getOrderId());
    }

    @Test
    void handleRejectOrderCommandProduct() {
        RejectOrderCommandProduct product = RejectOrderCommandProduct.builder()
            .orderId(FAKE_ORDER_ID)
            .productId(FAKE_PRODUCT_ID)
            .userId(FAKE_USER_ID)
            .reason(REASON)
            .paymentId(PAYMENT_ID)
            .build();
        OrderRejectedEvent event = OrderRejectedEvent.builder()
            .orderId(FAKE_ORDER_ID)
            .productId(FAKE_PRODUCT_ID)
            .userId(FAKE_USER_ID)
            .reason(REASON)
            .paymentId(PAYMENT_ID)
            .build();
        when(this.factory.readCommand(REJECT_ORDER_COMMAND_PRODUCT,ORDER_REJECTED_EVENT,product))
            .thenReturn(event);
        assertEquals(FAKE_ORDER_ID, this.listenerTest.handleRejectOrderCommandProduct(product)
            .getOrderId());
    }

    @Test
    void handleRejectOrderCommandPayment() {

        RejectOrderCommandPayment payment = RejectOrderCommandPayment.builder()
            .orderId(FAKE_ORDER_ID)
            .productId(FAKE_PRODUCT_ID)
            .userId(FAKE_USER_ID)
            .reason(REASON)
            .paymentId(PAYMENT_ID)
            .build();
        OrderRejectedEvent event = OrderRejectedEvent.builder()
            .orderId(FAKE_ORDER_ID)
            .productId(FAKE_PRODUCT_ID)
            .userId(FAKE_USER_ID)
            .reason(REASON)
            .paymentId(PAYMENT_ID)
            .build();

        when(this.factory.readCommand(REJECT_ORDER_COMMAND_PAYMENT,ORDER_REJECTED_EVENT,payment))
            .thenReturn(event);
        assertEquals(FAKE_ORDER_ID, this.listenerTest.handleRejectOrderCommandPayment(payment)
            .getOrderId());
    }
}