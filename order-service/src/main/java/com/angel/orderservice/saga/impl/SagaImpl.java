package com.angel.orderservice.saga.impl;

import com.angel.models.commands.*;
import com.angel.models.events.*;
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
@KafkaListener(topics = {ORDER_CREATED_EVENT, RESERVE_PRODUCT_COMMAND,
                         PROCESS_PAYMENT_COMMAND, APPROVE_ORDER_COMMAND,
                         CANCEL_PAYMENT_COMMAND, CANCEL_PRODUCT_RESERVATION_COMMAND,
                         REJECT_ORDER_COMMAND_PAYMENT, REJECT_ORDER_COMMAND_PRODUCT,
                         ORDER_APPROVED_EVENT, ORDER_REJECTED_EVENT},
                        groupId = GROUP_ID)
public class SagaImpl implements Saga {

    @Autowired
    private SendMessage sendService;

    @Autowired
    private OrdersServiceImpl ordersService;

    @Autowired
    private Factory factory;

    @Override//2
    @KafkaHandler
    public Command handleOrderCreatedEvent(OrderCreatedEvent event){
        Command command = this.factory.readEvent(ORDER_CREATED_EVENT, RESERVE_PRODUCT_COMMAND,event);
        this.sendService.sendMessage(RESERVE_PRODUCT_COMMAND, command);
        return command;
    }

    @Override//8
    @KafkaHandler
    public Command handleOrderApprovedEvent(OrderApprovedEvent event){
        this.ordersService.approveOrder(event.getOrderId());
        return null;
    }

    @Override//14
    @KafkaHandler
    public Command handleOrderRejectedEvent(OrderRejectedEvent event){
        this.ordersService.cancelOrder(event.getOrderId());
        return null;
    }
    //------------------------------------------------------------------------------------------------

    @Override//3
    @KafkaHandler
    public Event handleReserveProductCommand(ReserveProductCommand command){
        Event event = this.factory.readCommand(RESERVE_PRODUCT_COMMAND,
                                               PRODUCT_RESERVED_EVENT,
                                               command);
        this.sendService.sendMessage(PRODUCT_RESERVED_EVENT, event);
        return event;
    }

    @Override//5
    @KafkaHandler
    public Event handleProcessPaymentCommand(ProcessPaymentCommand command){
        Event event = this.factory.readCommand(PROCESS_PAYMENT_COMMAND,
                                               PAYMENT_PROCESSED_EVENT,
                                               command);
        this.sendService.sendMessage(PAYMENT_PROCESSED_EVENT, event);
        return event;
    }

    @Override//7
    @KafkaHandler
    public Event handleApproveOrderCommand(ApproveOrderCommand command){
        Event event = this.factory.readCommand(APPROVE_ORDER_COMMAND,
                                               ORDER_APPROVED_EVENT,
                                               command);
        this.sendService.sendMessage(ORDER_APPROVED_EVENT, event);
        return event;
    }

    @Override//11
    @KafkaHandler
    public Event handleCancelPaymentCommand(CancelPaymentCommand command){
        Event event = this.factory.readCommand(CANCEL_PAYMENT_COMMAND,
                                               PAYMENT_CANCELED_EVENT,
                                               command);
        this.sendService.sendMessage(PAYMENT_CANCELED_EVENT, event);
        return event;
    }

    @Override//9
    @KafkaHandler
    public Event handleCancelProductReservationCommand(ProductReservationCancelCommand command){
        Event event = this.factory.readCommand(CANCEL_PRODUCT_RESERVATION_COMMAND,
                                               PRODUCT_RESERVATION_CANCELED_EVENT,
                                               command);
        this.sendService.sendMessage(PRODUCT_RESERVATION_CANCELED_EVENT, event);
        return event;
    }

    @Override//13
    @KafkaHandler
    public Event handleRejectOrderCommandPayment(RejectOrderCommandPayment command){
        Event event = this.factory.readCommand(REJECT_ORDER_COMMAND_PAYMENT,
                                               ORDER_REJECTED_EVENT,
                                               command);
        this.sendService.sendMessage(ORDER_REJECTED_EVENT, event);
        return event;
    }

    @Override//15
    @KafkaHandler
    public Event handleRejectOrderCommandProduct(RejectOrderCommandProduct command){
        Event event = this.factory.readCommand(REJECT_ORDER_COMMAND_PRODUCT,
                                               ORDER_REJECTED_EVENT,
                                               command);
        this.sendService.sendMessage(ORDER_REJECTED_EVENT, event);
        return event;
    }

}
