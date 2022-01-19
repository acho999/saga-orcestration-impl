package com.angel.orderservice.saga.impl;

import com.angel.models.commands.*;
import com.angel.models.events.*;
import com.angel.models.states.PaymentState;
import com.angel.orderservice.services.impl.OrdersServiceImpl;
import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import com.angel.orderservice.saga.api.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.angel.models.constants.TopicConstants.*;

@Component
@KafkaListener(topics = {ORDER_CREATED_EVENT, PRODUCT_RESERVED_EVENT,
                         PAYMENT_PROCESSED_EVENT, PAYMENT_CANCELED_EVENT,
                         PRODUCT_RESERVATION_CANCELED_EVENT,
                         ORDER_APPROVED_EVENT, ORDER_REJECTED_EVENT}, groupId = GROUP_ID)
public class SagaImpl implements Saga {

    @Autowired
    private SendMessage sendService;

    @Autowired
    private OrdersServiceImpl ordersService;

    @Autowired
    private Factory factory;

    @Override//2
    @KafkaHandler
    public Command handleOrderCreatedEvent(OrderCreatedEvent event) {

        Command command = (ReserveProductCommand) this.factory
            .readEvent(ORDER_CREATED_EVENT, RESERVE_PRODUCT_COMMAND,
                       event);

        this.sendService.sendMessage(RESERVE_PRODUCT_COMMAND, command);
        return command;
    }

    @Override//3
    @KafkaHandler
    public Command handleProductReservedEvent(ProductReservedEvent event) {

        ProcessPaymentCommand command = (ProcessPaymentCommand) this.factory
            .readEvent(PRODUCT_RESERVED_EVENT, PROCESS_PAYMENT_COMMAND,
                       event);

        this.sendService.sendMessage(PROCESS_PAYMENT_COMMAND, command);
        return command;
    }

    @Override//5
    @KafkaHandler
    public Command handlePaymentProcessedEvent(PaymentProcessedEvent event) {

        ApproveOrderCommand command = (ApproveOrderCommand) this.factory
            .readEvent(PAYMENT_PROCESSED_EVENT, APPROVE_ORDER_COMMAND,
                       event);

        this.sendService.sendMessage(APPROVE_ORDER_COMMAND, command);
        return command;
    }

    @Override//8 order created successfully
    @KafkaHandler
    public void handleOrderApprovedEvent(OrderApprovedEvent event) {
        this.ordersService.approveOrder(event.getOrderId());
    }

    @Override//10
    @KafkaHandler
    public Command handleProductReservationCanceledEvent(ProductReservationCanceledEvent event) {

        RejectOrderCommandProduct command = (RejectOrderCommandProduct) this.factory
            .readEvent(PRODUCT_RESERVATION_CANCELED_EVENT, REJECT_ORDER_COMMAND_PRODUCT,
                       event);

        ProductReservationCancelCommand cancelProdRes = ProductReservationCancelCommand.builder()
            .productId(event.getProductId())
            .orderId(event.getOrderId())
            .price(event.getPrice())
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
    public Command handlePaymentCanceledEvent(PaymentCanceledEvent event) {

        RejectOrderCommandProduct command = (RejectOrderCommandProduct) this.factory
            .readEvent(PAYMENT_CANCELED_EVENT, REJECT_ORDER_COMMAND_PAYMENT,
                       event);

        CancelPaymentCommand cancelPayment = CancelPaymentCommand.builder()
            .productId(event.getProductId())
            .orderId(event.getOrderId())
            .price(event.getPrice())
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
    public void handleOrderRejectedEvent(OrderRejectedEvent event) {
        this.ordersService.cancelOrder(event.getOrderId());
    }

}
