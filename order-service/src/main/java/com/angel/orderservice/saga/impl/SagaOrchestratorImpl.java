package com.angel.orderservice.saga.impl;

import com.angel.models.commands.*;
import com.angel.models.entities.Product;
import com.angel.models.events.*;
import com.angel.models.states.PaymentState;
import com.angel.orderservice.services.impl.OrdersServiceImpl;
import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import com.angel.orderservice.saga.api.SagaOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.angel.models.constants.TopicConstants.*;
import static com.angel.models.constants.CommonConstants.EVENT_CAN_NOT_BE_NULL;

@Component
@KafkaListener(topics = {ORDER_CREATED_EVENT, PRODUCT_RESERVED_EVENT,
                         PAYMENT_PROCESSED_EVENT, PAYMENT_CANCELED_EVENT,
                         PRODUCT_RESERVATION_CANCELED_EVENT,
                         ORDER_APPROVED_EVENT, ORDER_REJECTED_EVENT}, groupId = GROUP_ID)
public class SagaOrchestratorImpl implements SagaOrchestrator {

    private final SendMessage sendService;
    private final OrdersServiceImpl ordersService;
    private final Factory factory;

    @Autowired
    public SagaOrchestratorImpl(SendMessage sendService,
                                OrdersServiceImpl ordersService, Factory factory) {
        this.sendService = sendService;
        this.ordersService = ordersService;
        this.factory = factory;
    }

    @Override//2
    @KafkaHandler
    public Command handleOrderCreatedEvent(@Payload OrderCreatedEvent event){

        if( Objects.isNull(event)){
            throw new IllegalArgumentException(EVENT_CAN_NOT_BE_NULL);
        }
        ReserveProductCommand command = (ReserveProductCommand) this.factory
            .readEvent(ORDER_CREATED_EVENT, RESERVE_PRODUCT_COMMAND,
                       event);
        Product prod = this.factory.createProduct();
        prod.setId(command.getProductId());

        this.sendService.sendMessage(SET_PRODUCT_PRICE, prod);
        this.sendService.sendMessage(RESERVE_PRODUCT_COMMAND, command);
        return command;
    }

    @Override//3
    @KafkaHandler
    public Command handleProductReservedEvent(@Payload ProductReservedEvent event) {
        if( Objects.isNull(event)){
            throw new IllegalArgumentException(EVENT_CAN_NOT_BE_NULL);
        }
        ProcessPaymentCommand command = (ProcessPaymentCommand) this.factory
            .readEvent(PRODUCT_RESERVED_EVENT, PROCESS_PAYMENT_COMMAND,
                       event);
        this.sendService.sendMessage(PROCESS_PAYMENT_COMMAND, command);
        return command;
    }

    @Override//5
    @KafkaHandler
    public Command handlePaymentProcessedEvent(@Payload PaymentProcessedEvent event) {
        if( Objects.isNull(event)){
            throw new IllegalArgumentException(EVENT_CAN_NOT_BE_NULL);
        }
        ApproveOrderCommand command = (ApproveOrderCommand) this.factory
            .readEvent(PAYMENT_PROCESSED_EVENT, APPROVE_ORDER_COMMAND,
                       event);

        this.sendService.sendMessage(APPROVE_ORDER_COMMAND, command);
        return command;
    }

    @Override//8 order created successfully
    @KafkaHandler
    public void handleOrderApprovedEvent(@Payload OrderApprovedEvent event) {
        if( Objects.isNull(event)){
            throw new IllegalArgumentException(EVENT_CAN_NOT_BE_NULL);
        }
        this.ordersService.approveOrder(event.getOrderId());
    }

    @Override//10
    @KafkaHandler
    public Command handleProductReservationCanceledEvent(@Payload ProductReservationCanceledEvent event) {
        if( Objects.isNull(event)){
            throw new IllegalArgumentException(EVENT_CAN_NOT_BE_NULL);
        }
        RejectOrderCommandProduct command = (RejectOrderCommandProduct) this.factory
            .readEvent(PRODUCT_RESERVATION_CANCELED_EVENT, REJECT_ORDER_COMMAND_PRODUCT,
                       event);

        ProductReservationCancelCommand cancelProdRes = ProductReservationCancelCommand.builder()
            .productId(event.getProductId())
            .orderId(event.getOrderId())
            .quantity(event.getQuantity())
            .userId(event.getUserId())
            .reason(event.getReason())
            .build();
        cancelProdRes.setPaymentState(PaymentState.REJECTED);

        this.sendService.sendMessage(CANCEL_PRODUCT_RESERVATION_COMMAND, cancelProdRes);
        this.sendService.sendMessage(REJECT_ORDER_COMMAND_PRODUCT, command);
        return command;
    }

    @Override//12
    @KafkaHandler
    public Command handlePaymentCanceledEvent(@Payload PaymentCanceledEvent event) {
        if( Objects.isNull(event)){
            throw new IllegalArgumentException(EVENT_CAN_NOT_BE_NULL);
        }
        RejectOrderCommandProduct command = (RejectOrderCommandProduct) this.factory
            .readEvent(PAYMENT_CANCELED_EVENT, REJECT_ORDER_COMMAND_PAYMENT,
                       event);

        CancelPaymentCommand cancelPayment = CancelPaymentCommand.builder()
            .productId(event.getProductId())
            .orderId(event.getOrderId())
            .quantity(event.getQuantity())
            .userId(event.getUserId())
            .paymentId(event.getPaymentId())
            .paymentState(event.getPaymentState())
            .build();

        this.sendService.sendMessage(CANCEL_PAYMENT_COMMAND, cancelPayment);
        this.sendService.sendMessage(REJECT_ORDER_COMMAND_PAYMENT, command);
        return command;
    }

    @Override//14
    @KafkaHandler
    public void handleOrderRejectedEvent(@Payload OrderRejectedEvent event) {
        if( Objects.isNull(event)){
            throw new IllegalArgumentException(EVENT_CAN_NOT_BE_NULL);
        }
        this.ordersService.cancelOrder(event.getOrderId());
    }

}
