package com.angel.orderservice.saga.impl;

import com.angel.models.commands.*;
import com.angel.models.events.*;
import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.angel.orderservice.saga.api.Saga;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.angel.models.constants.TopicConstants.*;

@Component
public class SagaImpl implements Saga {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SendMessage sendService;

    @Value(value = "${kafka.groupId}")
    private String group;

    private static final String droupId = "newApp11";

    @Autowired
    private Factory factory;

    @Override//2
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Command handleOrderCreatedEvent(String message)
        throws JsonProcessingException {
//1
        Command command = this.factory.readEvent(ORDER_CREATED_EVENT,RESERVE_PRODUCT_COMMAND,
                                                  new OrderCreatedEvent(
                                                      null,
                                                      null,
                                                      null,
                                                      0,
                                                      null,
                                                      0.0d), message);
        this.sendService.sendMessage(RESERVE_PRODUCT_COMMAND, command, this.mapper);
        return command;
    }

    @Override//4
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Command handleProductReservedEvent(String message)
        throws JsonProcessingException {

        Command command = this.factory.readEvent(PRODUCT_RESERVED_EVENT,PROCESS_PAYMENT_COMMAND,
                                                  new ProductReservedEvent(
                                                      null,
                                                      null,
                                                      null,
                                                      0,
                                                      0.0d),message);
        this.sendService.sendMessage(PROCESS_PAYMENT_COMMAND, command, this.mapper);
        return command;
    }

    @Override//6
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Command handlePaymentProcessedEvent(String message)
        throws JsonProcessingException {

        Command command = this.factory.readEvent(PAYMENT_PROCESSED_EVENT,APPROVE_ORDER_COMMAND,
                                                  new PaymentProcessedEvent(
                                                      null,
                                                      null,
                                                      null,
                                                      null,
                                                      0.0d,
                                                      0,
                                                      null), message);
        this.sendService.sendMessage(APPROVE_ORDER_COMMAND, command, this.mapper);
        return command;
    }

    @Override//8
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Command handleOrderApprovedEvent(String message)
        throws JsonProcessingException {

        Command command = this.factory.readEvent(ORDER_APPROVED_EVENT,APPROVE_ORDER_COMMAND,
                                                  new OrderApprovedEvent(
                                                      null,
                                                      null,
                                                      null,
                                                      null,
                                                      0),message);
        //this.sendService.sendMessage();se(???, command);
        return command;
    }

    @Override//10
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Command handleProductReservationCanceledEvent(String message)
        throws JsonProcessingException {
        Command command = this.factory.readEvent(PRODUCT_RESERVATION_CANCELED_EVENT,REJECT_ORDER_COMMAND,
                                                  new ProductReservationCalseledEvent(
                                                      null,
                                                      0,
                                                      null,
                                                      null,
                                                      null),message);
        this.sendService.sendMessage(REJECT_ORDER_COMMAND, command, this.mapper);
        return command;
    }

    @Override//12
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Command handlePaymentCanceledEvent(String message)
        throws JsonProcessingException {

        Command command = this.factory.readEvent(PAYMENT_CANCELED_EVENT,REJECT_ORDER_COMMAND,
                                                  new PaymentCanceledEvent(
                                                      null,
                                                      null,
                                                      null,
                                                      null,
                                                      0), message);
        this.sendService.sendMessage(REJECT_ORDER_COMMAND, command, this.mapper);
        return command;
    }

    @Override//14
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Command handleOrderRejectedEvent(String message)
        throws JsonProcessingException {

        Command command = this.factory.readEvent(ORDER_REJECTED_EVENT,ORDER_REJECTED_EVENT,
                                                  new OrderRejectedEvent(
                                                      null,
                                                      null,
                                                      null,
                                                      null),message);
        //this.sendService.sendMessage();(ORDER_REJECTED_EVENT, command);
        return command;
    }
    //------------------------------------------------------------------------------------------------

    @Override//1
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Event publishCreateOrderCommand(Command command, String message)
        throws JsonProcessingException {
        Event event = this.factory.readCommand(CREATE_ORDER_COMMAND,ORDER_CREATED_EVENT,command, message);
        this.sendService.sendMessage(ORDER_CREATED_EVENT, event, this.mapper);
        return event;
    }

    @Override//3
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Event publishReserveProductCommand(String message)
        throws JsonProcessingException {

        Event event = this.factory.readCommand(RESERVE_PRODUCT_COMMAND,
                                                PRODUCT_RESERVED_EVENT,
                                                new ReserveProductCommand(
                                                    null,
                                                    null,
                                                    null,
                                                    0,
                                                    0.0d),message);
        this.sendService.sendMessage(PRODUCT_RESERVED_EVENT, event, this.mapper);
        return event;
    }

    @Override//5
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Event publishProcessPaymentCommand(String message)
        throws JsonProcessingException {

        Event event = this.factory.readCommand(PROCESS_PAYMENT_COMMAND,
                                                PAYMENT_PROCESSED_EVENT,
                                                new ProcessPaymentCommand(
                                                    null,
                                                    null,
                                                    null,
                                                    null,
                                                    0.0d,
                                                    0,
                                                    null),message);
        this.sendService.sendMessage(PAYMENT_PROCESSED_EVENT, event, this.mapper);
        return event;
    }

    @Override//7
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Event publishApproveOrderCommand(String message)
        throws JsonProcessingException {
        Event event = this.factory.readCommand(APPROVE_ORDER_COMMAND,
                                                ORDER_APPROVED_EVENT,
                                                new ApproveOrderCommand(
                                                  null,
                                                  null,
                                                  null,
                                                  0),message);
        this.sendService.sendMessage(ORDER_APPROVED_EVENT, event, this.mapper);
        return event;
    }

    @Override//9
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Event publishCancelProductReservationCommand(String message)
        throws JsonProcessingException {
        Event event = this.factory.readCommand(CANCEL_PRODUCT_RESERVATION_COMMAND,
                                                PRODUCT_RESERVATION_CANCELED_EVENT,
                                                new ProductReservationCanselCommand(
                                                    null,
                                                    0,
                                                    null,
                                                    null,
                                                    null),message);
        this.sendService.sendMessage(PRODUCT_RESERVATION_CANCELED_EVENT, event, this.mapper);
        return event;
    }

    @Override//11
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Event publishCancelPaymentCommand(String message)
        throws JsonProcessingException {
        Event event = this.factory.readCommand(CANCEL_PAYMENT_COMMAND,
                                                PAYMENT_CANCELED_EVENT,
                                                new CancelPaymentCommand(
                                                    null,
                                                    null,
                                                    null,
                                                    null,0),message);
        this.sendService.sendMessage(PAYMENT_CANCELED_EVENT, event, this.mapper);
        return event;
    }

    @Override//13
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Event publishRejectOrderCommand(String message)
        throws JsonProcessingException {
        Event event = this.factory.readCommand(REJECT_ORDER_COMMAND,
                                                ORDER_REJECTED_EVENT,
                                                new RejectOrderCommand(
                                                    null,
                                                    null,
                                                    null,
                                                    null),message);
        this.sendService.sendMessage(ORDER_REJECTED_EVENT, event, this.mapper);
        return event;
    }

}
