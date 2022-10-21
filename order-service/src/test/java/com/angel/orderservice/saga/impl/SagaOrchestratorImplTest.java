package com.angel.orderservice.saga.impl;

import com.angel.models.commands.ApproveOrderCommand;
import com.angel.models.commands.ProcessPaymentCommand;
import com.angel.models.commands.ProductReservationCancelCommand;
import com.angel.models.commands.RejectOrderCommandPayment;
import com.angel.models.commands.RejectOrderCommandProduct;
import com.angel.models.commands.ReserveProductCommand;
import com.angel.models.entities.Product;
import com.angel.models.events.OrderApprovedEvent;
import com.angel.models.events.OrderCreatedEvent;
import com.angel.models.events.OrderRejectedEvent;
import com.angel.models.events.PaymentCanceledEvent;
import com.angel.models.events.PaymentProcessedEvent;
import com.angel.models.events.ProductReservationCanceledEvent;
import com.angel.models.events.ProductReservedEvent;
import com.angel.models.states.OrderState;
import com.angel.models.states.PaymentState;
import com.angel.orderservice.services.impl.OrdersServiceImpl;
import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.angel.models.constants.CommonConstants.EVENT_CAN_NOT_BE_NULL;
import static com.angel.models.constants.CommonConstants.FAKE_ORDER_ID;
import static com.angel.models.constants.CommonConstants.FAKE_PRODUCT_ID;
import static com.angel.models.constants.CommonConstants.FAKE_USER_ID;
import static com.angel.models.constants.CommonConstants.PAYMENT_ID;
import static com.angel.models.constants.CommonConstants.QUANTITY;
import static com.angel.models.constants.TopicConstants.APPROVE_ORDER_COMMAND;
import static com.angel.models.constants.TopicConstants.ORDER_CREATED_EVENT;
import static com.angel.models.constants.TopicConstants.PAYMENT_CANCELED_EVENT;
import static com.angel.models.constants.TopicConstants.PAYMENT_PROCESSED_EVENT;
import static com.angel.models.constants.TopicConstants.PROCESS_PAYMENT_COMMAND;
import static com.angel.models.constants.TopicConstants.PRODUCT_RESERVATION_CANCELED_EVENT;
import static com.angel.models.constants.TopicConstants.PRODUCT_RESERVED_EVENT;
import static com.angel.models.constants.TopicConstants.REJECT_ORDER_COMMAND_PAYMENT;
import static com.angel.models.constants.TopicConstants.REJECT_ORDER_COMMAND_PRODUCT;
import static com.angel.models.constants.TopicConstants.RESERVE_PRODUCT_COMMAND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SagaOrchestratorImplTest {

    @InjectMocks
    private SagaOrchestratorImpl orchestratorTest;
    @Mock private  SendMessage sendService;
    @Mock private  OrdersServiceImpl ordersService;
    @Mock private  Factory factory;

    @Test
    void handleOrderCreatedEvent() {
        Product product = Product.builder()
            .id(FAKE_PRODUCT_ID)
            .name("shoes")
            .quantity(QUANTITY)
            .description("trainers")
            .price(10.0d)
            .build();
        OrderCreatedEvent event = OrderCreatedEvent.builder()
            .orderId(FAKE_ORDER_ID)
            .productId(FAKE_PRODUCT_ID)
            .userId(FAKE_USER_ID)
            .quantity(QUANTITY)
            .state(OrderState.PENDING)
            .build();
        ReserveProductCommand command = ReserveProductCommand.builder()
            .orderId(FAKE_ORDER_ID)
            .productId(FAKE_PRODUCT_ID)
            .userId(FAKE_USER_ID)
            .quantity(QUANTITY)
            .build();
        when(this.factory.createProduct()).thenReturn(product);
        when(this.factory.readEvent(ORDER_CREATED_EVENT, RESERVE_PRODUCT_COMMAND, event))
            .thenReturn(command);
        assertEquals(FAKE_ORDER_ID, this.orchestratorTest.handleOrderCreatedEvent(event)
            .getOrderId());


        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,()->{
                this.orchestratorTest.handleOrderCreatedEvent(null);
            }
        );
        assertEquals(EVENT_CAN_NOT_BE_NULL, ex.getMessage());
    }

    @Test
    void handleProductReservedEvent() {
        ProductReservedEvent event = ProductReservedEvent.builder()
            .orderId(FAKE_ORDER_ID)
            .productId(FAKE_PRODUCT_ID)
            .userId(FAKE_USER_ID)
            .quantity(QUANTITY)
            .build();
        ProcessPaymentCommand command = ProcessPaymentCommand.builder()
            .orderId(FAKE_ORDER_ID)
            .productId(FAKE_PRODUCT_ID)
            .userId(FAKE_USER_ID)
            .quantity(QUANTITY)
            .paymentId(PAYMENT_ID)
            .paymentState(PaymentState.APPROVED)
            .build();
        when(this.factory.readEvent(PRODUCT_RESERVED_EVENT, PROCESS_PAYMENT_COMMAND,event))
            .thenReturn(command);
        assertEquals(FAKE_ORDER_ID, this.orchestratorTest.handleProductReservedEvent(event)
            .getOrderId());

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,()->{
                this.orchestratorTest.handleProductReservedEvent(null);
            }
        );
        assertEquals(EVENT_CAN_NOT_BE_NULL, ex.getMessage());
    }

    @Test
    void handlePaymentProcessedEvent() {
        PaymentProcessedEvent event = PaymentProcessedEvent.builder()
            .orderId(FAKE_ORDER_ID)
            .productId(FAKE_PRODUCT_ID)
            .userId(FAKE_USER_ID)
            .quantity(QUANTITY)
            .paymentId(PAYMENT_ID)
            .paymentState(PaymentState.APPROVED)
            .build();
        ApproveOrderCommand command = ApproveOrderCommand.builder()
            .orderId(FAKE_ORDER_ID)
            .productId(FAKE_PRODUCT_ID)
            .userId(FAKE_USER_ID)
            .quantity(QUANTITY)
            .build();
        when(this.factory.readEvent(PAYMENT_PROCESSED_EVENT, APPROVE_ORDER_COMMAND,event))
            .thenReturn(command);
        assertEquals(FAKE_ORDER_ID, this.orchestratorTest.handlePaymentProcessedEvent(event)
            .getOrderId());
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,()->{
                this.orchestratorTest.handlePaymentProcessedEvent(null);
            }
        );
        assertEquals(EVENT_CAN_NOT_BE_NULL, ex.getMessage());
    }

    @Test
    void handleOrderApprovedEvent() {
        OrderApprovedEvent event = OrderApprovedEvent.builder()
            .orderId(FAKE_ORDER_ID)
            .productId(FAKE_PRODUCT_ID)
            .userId(FAKE_USER_ID)
            .quantity(QUANTITY)
            .state(OrderState.CREATED)
            .build();
        when(this.ordersService.approveOrder(FAKE_ORDER_ID)).thenReturn(true);
        assertTrue(this.orchestratorTest.handleOrderApprovedEvent(event));

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,()->{
                this.orchestratorTest.handleOrderApprovedEvent(null);
            }
        );
        assertEquals(EVENT_CAN_NOT_BE_NULL, ex.getMessage());
    }

    @Test
    void handleProductReservationCanceledEvent() {
        ProductReservationCanceledEvent event = ProductReservationCanceledEvent.builder()
            .orderId(FAKE_ORDER_ID)
            .productId(FAKE_PRODUCT_ID)
            .userId(FAKE_USER_ID)
            .quantity(QUANTITY)
            .paymentId(PAYMENT_ID)
            .paymentState(PaymentState.APPROVED)
            .build();
        RejectOrderCommandProduct command = RejectOrderCommandProduct.builder()
            .orderId(FAKE_ORDER_ID)
            .productId(FAKE_PRODUCT_ID)
            .userId(FAKE_USER_ID)
            .paymentId(PAYMENT_ID)
            .reason("reason")
            .build();
        when(this.factory.readEvent(PRODUCT_RESERVATION_CANCELED_EVENT, REJECT_ORDER_COMMAND_PRODUCT,event))
            .thenReturn(command);
        assertEquals(FAKE_ORDER_ID, this.orchestratorTest.handleProductReservationCanceledEvent(event)
            .getOrderId());

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,()->{
                this.orchestratorTest.handleProductReservationCanceledEvent(null);
            }
        );
        assertEquals(EVENT_CAN_NOT_BE_NULL, ex.getMessage());
    }

    @Test
    void handlePaymentCanceledEvent() {
        PaymentCanceledEvent event = PaymentCanceledEvent.builder()
            .orderId(FAKE_ORDER_ID)
            .productId(FAKE_PRODUCT_ID)
            .userId(FAKE_USER_ID)
            .quantity(QUANTITY)
            .paymentId(PAYMENT_ID)
            .paymentState(PaymentState.APPROVED)
            .build();
        RejectOrderCommandPayment command = RejectOrderCommandPayment.builder()
            .orderId(FAKE_ORDER_ID)
            .productId(FAKE_PRODUCT_ID)
            .userId(FAKE_USER_ID)
            .paymentId(PAYMENT_ID)
            .reason("reason")
            .build();
        when(this.factory.readEvent(PAYMENT_CANCELED_EVENT, REJECT_ORDER_COMMAND_PAYMENT,
                                    event)).thenReturn(command);
        assertEquals(FAKE_ORDER_ID, this.orchestratorTest.handlePaymentCanceledEvent(event)
            .getOrderId());

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,()->{
                this.orchestratorTest.handlePaymentCanceledEvent(null);
            }
        );
        assertEquals(EVENT_CAN_NOT_BE_NULL, ex.getMessage());
    }

    @Test
    void handleOrderRejectedEvent() {
        OrderRejectedEvent event = OrderRejectedEvent.builder()
            .orderId(FAKE_ORDER_ID)
            .productId(FAKE_PRODUCT_ID)
            .userId(FAKE_USER_ID)
            .paymentId(PAYMENT_ID)
            .reason("reason")
            .build();

        when(this.ordersService.cancelOrder(FAKE_ORDER_ID)).thenReturn(true);
        assertTrue(this.orchestratorTest.handleOrderRejectedEvent(event));
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,()->{
                this.orchestratorTest.handleOrderRejectedEvent(null);
            }
        );
        assertEquals(EVENT_CAN_NOT_BE_NULL, ex.getMessage());
    }
}