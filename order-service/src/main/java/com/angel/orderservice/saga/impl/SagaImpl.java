package com.angel.orderservice.saga.impl;

import com.angel.models.commands.*;
import com.angel.models.events.*;
import com.angel.orderservice.services.impl.OrdersServiceImpl;
import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.angel.orderservice.saga.api.Saga;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.angel.models.constants.TopicConstants.*;

@Component
public class SagaImpl implements Saga {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SendMessage sendService;

    @Autowired
    private OrdersServiceImpl ordersService;

    private static final String droupId = GROUP_ID;

    @Autowired
    private Factory factory;

    @Override//2
    @KafkaListener(topics = ORDER_CREATED_EVENT, groupId = droupId)
    public synchronized Command handleOrderCreatedEvent(String message)
        throws JsonProcessingException {
        //1
        System.out.println("handleOrderCreatedEvent");
        Command command = this.factory.readEvent(ORDER_CREATED_EVENT,RESERVE_PRODUCT_COMMAND,
                                                  new OrderCreatedEvent(), message);
        this.sendService.sendMessage(RESERVE_PRODUCT_COMMAND, command, this.mapper);
        return command;
    }


    @Override//8
    @KafkaListener(topics = ORDER_APPROVED_EVENT, groupId = droupId)
    public synchronized Command handleOrderApprovedEvent(String message)
        throws JsonProcessingException {
        System.out.println("handleOrderApprovedEvent" + message);
        JsonNode id = this.factory.convertJsonToJsonNode(message).get("orderId");
        System.out.println(id);
        this.ordersService.approveOrder(id.asText());
        return null;
    }

    @Override//14
    @KafkaListener(topics = ORDER_REJECTED_EVENT, groupId = droupId)
    public synchronized Command handleOrderRejectedEvent(String message)
        throws JsonProcessingException {
        System.out.println("handleOrderRejectedEvent");
        JsonNode id = this.factory.convertJsonToJsonNode(message).get("orderId");
        this.ordersService.cancelOrder(id.asText());
        return null;
    }
    //------------------------------------------------------------------------------------------------

    @Override//1
    @KafkaListener(topics = CREATE_ORDER_COMMAND, groupId = droupId)
    public synchronized Event publishCreateOrderCommand(String message)
        throws JsonProcessingException {
        System.out.println("publishCreateOrderCommand");
        Event event = this.factory.readCommand(CREATE_ORDER_COMMAND,ORDER_CREATED_EVENT,new CreateOrderCommand(), message);
        this.sendService.sendMessage(ORDER_CREATED_EVENT, event, this.mapper);
        return event;
    }

    @Override//3
    @KafkaListener(topics = RESERVE_PRODUCT_COMMAND, groupId = droupId)
    public synchronized Event publishReserveProductCommand(String message)
        throws JsonProcessingException {
        System.out.println("publishReserveProductCommand");
        Event event = this.factory.readCommand(RESERVE_PRODUCT_COMMAND,
                                                PRODUCT_RESERVED_EVENT,
                                                new ReserveProductCommand(),message);
        this.sendService.sendMessage(PRODUCT_RESERVED_EVENT, event, this.mapper);
        return event;
    }

    @Override//5
    @KafkaListener(topics = PROCESS_PAYMENT_COMMAND, groupId = droupId)
    public synchronized Event publishProcessPaymentCommand(String message)
        throws JsonProcessingException {
        System.out.println("publishProcessPaymentCommand");
        Event event = this.factory.readCommand(PROCESS_PAYMENT_COMMAND,
                                                PAYMENT_PROCESSED_EVENT,
                                                new ProcessPaymentCommand(),message);
        this.sendService.sendMessage(PAYMENT_PROCESSED_EVENT, event, this.mapper);
        return event;
    }

    @Override//7
    @KafkaListener(topics = APPROVE_ORDER_COMMAND, groupId = droupId)
    public synchronized Event publishApproveOrderCommand(String message)
        throws JsonProcessingException {
        System.out.println("publishApproveOrderCommand");
        Event event = this.factory.readCommand(APPROVE_ORDER_COMMAND,
                                                ORDER_APPROVED_EVENT,
                                                new ApproveOrderCommand(),message);
        this.sendService.sendMessage(ORDER_APPROVED_EVENT, event, this.mapper);
        return event;
    }

    @Override//11
    @KafkaListener(topics = CANCEL_PAYMENT_COMMAND, groupId = droupId)
    public synchronized Event publishCancelPaymentCommand(String message)
        throws JsonProcessingException {
        System.out.println("publishCancelPaymentCommand");
        Event event = this.factory.readCommand(CANCEL_PAYMENT_COMMAND,
                                               PAYMENT_CANCELED_EVENT,
                                               new CancelPaymentCommand(),message);
        this.sendService.sendMessage(PAYMENT_CANCELED_EVENT, event, this.mapper);
        return event;
    }

    @Override//9
    @KafkaListener(topics = CANCEL_PRODUCT_RESERVATION_COMMAND, groupId = droupId)
    public synchronized Event publishCancelProductReservationCommand(String message)
        throws JsonProcessingException {
        System.out.println("publishCancelProductReservationCommand");
        Event event = this.factory.readCommand(CANCEL_PRODUCT_RESERVATION_COMMAND,
                                               PRODUCT_RESERVATION_CANCELED_EVENT,
                                               new ProductReservationCancelCommand(), message);
        this.sendService.sendMessage(PRODUCT_RESERVATION_CANCELED_EVENT, event, this.mapper);
        return event;
    }

    @Override//13
    @KafkaListener(topics = REJECT_ORDER_COMMAND_PAYMENT, groupId = droupId)
    public synchronized Event publishRejectOrderCommandPayment(String message)
        throws JsonProcessingException {
        System.out.println("publishRejectOrderCommand");
        Event event = this.factory.readCommand(REJECT_ORDER_COMMAND_PAYMENT,
                                                ORDER_REJECTED_EVENT,
                                                new RejectOrderCommand(),message);
        this.sendService.sendMessage(ORDER_REJECTED_EVENT, event, this.mapper);
        return event;
    }

    @Override//15
    @KafkaListener(topics = REJECT_ORDER_COMMAND_PRODUCT, groupId = droupId)
    public synchronized Event publishRejectOrderCommandProduct(String message)
        throws JsonProcessingException {
        System.out.println("publishRejectOrderCommand");
        Event event = this.factory.readCommand(REJECT_ORDER_COMMAND_PRODUCT,
                                               ORDER_REJECTED_EVENT,
                                               new RejectOrderCommand(),message);
        this.sendService.sendMessage(ORDER_REJECTED_EVENT, event, this.mapper);
        return event;
    }

}
