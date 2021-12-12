package com.angel.saga.impl;

import com.angel.kafkaconsumer.consumer.IKafkaConsumerConfig;
import com.angel.kafkaproducer.producer.IKafkaProducerConfig;
import com.angel.models.commands.*;
import com.angel.models.events.*;
import com.angel.saga.api.SagaOrchestration;
import com.angel.models.commands.Command;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Properties;

import static com.angel.models.constants.TopicConstants.*;

public class SagaOrchestrationImpl implements SagaOrchestration {

    @Autowired
    private IKafkaProducerConfig producer;

    @Autowired
    private IKafkaConsumerConfig consumer;

    @Override//2
    public Command handleOrderCreatedEvent(ConsumerRecord<String, String> record)
        throws JsonProcessingException {

        Command command = this.consumer.readEvent(ORDER_CREATED_EVENT,RESERVE_PRODUCT_COMMAND,
                                                  new OrderCreatedEvent(
                                                      null,
                                                      null,
                                                      null,
                                                      0,
                                                      null), record);
        this.producer.sendCommand(PROCESS_PAYMENT_COMMAND, command);
        return command;
    }

    @Override//4
    public Command handleProductReservedEvent(ConsumerRecord<String, String> record)
        throws JsonProcessingException {

        Command command = this.consumer.readEvent(PRODUCT_RESERVED_EVENT,PROCESS_PAYMENT_COMMAND,
                                                  new ProductReservedEvent(
                                                      null,
                                                      null,
                                                      null,
                                                      0,
                                                      0.0d),record);
        this.producer.sendCommand(PROCESS_PAYMENT_COMMAND, command);
        return command;
    }

    @Override//6
    public Command handlePaymentProcessedEvent(ConsumerRecord<String, String> record)
        throws JsonProcessingException {

        Command command = this.consumer.readEvent(PAYMENT_PROCESSED_EVENT,APPROVE_ORDER_COMMAND,
                                                  new PaymentProcessedEvent(
                                                      null,
                                                      null,
                                                      null,
                                                      null,
                                                      0.0d,
                                                      0), record);
        this.producer.sendCommand(APPROVE_ORDER_COMMAND, command);
        return command;
    }

    @Override//8
    public Command handleOrderApprovedEvent(ConsumerRecord<String, String> record)
        throws JsonProcessingException {

        Command command = this.consumer.readEvent(ORDER_APPROVED_EVENT,APPROVE_ORDER_COMMAND,
                                                  new OrderApprovedEvent(
                                                      null,
                                                      null,
                                                      null,
                                                      null,
                                                      0),record);
        //this.producer.sendCommand(???, command);
        return command;
    }

    @Override//10
    public Command handleProductReservationCanceledEvent(ConsumerRecord<String, String> record)
        throws JsonProcessingException {
        Command command = this.consumer.readEvent(PRODUCT_RESERVATION_CANCELED_EVENT,REJECT_ORDER_COMMAND,
                                                  new ProductReservationCalseledEvent(
                                                      null,
                                                      0,
                                                      null,
                                                      null,
                                                      null),record);
        this.producer.sendCommand(REJECT_ORDER_COMMAND, command);
        return command;
    }

    @Override//12
    public Command handlePaymentCanceledEvent(ConsumerRecord<String, String> record)
        throws JsonProcessingException {

        Command command = this.consumer.readEvent(PAYMENT_CANCELED_EVENT,REJECT_ORDER_COMMAND,
                                                  new PaymentCanceledEvent(
                                                      null,
                                                      null,
                                                      null,
                                                      null,
                                                      0), record);
        this.producer.sendCommand(REJECT_ORDER_COMMAND, command);
        return command;
    }

    @Override//14
    public Command handleOrderRejectedEvent(ConsumerRecord<String, String> record)
        throws JsonProcessingException {

        Command command = this.consumer.readEvent(ORDER_REJECTED_EVENT,ORDER_REJECTED_EVENT,
                                                  new OrderRejectedEvent(
                                                      null,
                                                      null,
                                                      null,
                                                      null),record);
        //this.producer.sendCommand(ORDER_REJECTED_EVENT, command);
        return command;
    }

    public void testConsumer(){
        this.consumer.consumeTest();
    }

    //------------------------------------------------------------------------------------------------

    @Override//1
    public Event publishCreateOrderCommand(Command command, ConsumerRecord<String, String> record)
        throws JsonProcessingException {
        Event event = this.consumer.readCommand(CREATE_ORDER_COMMAND,ORDER_CREATED_EVENT,command, record);
        this.producer.sendEvent(ORDER_CREATED_EVENT, event);
        return event;
    }

    @Override//3
    public Event publishReserveProductCommand(ConsumerRecord<String, String> record)
        throws JsonProcessingException {

        Event event = this.consumer.readCommand(RESERVE_PRODUCT_COMMAND,
                                                PRODUCT_RESERVED_EVENT,
                                                new ReserveProductCommand(
                                                    null,
                                                    null,
                                                    null,
                                                    0,
                                                    0.0d),record);
        this.producer.sendEvent(PRODUCT_RESERVED_EVENT, event);
        return event;
    }

    @Override//5
    public Event publishProcessPaymentCommand(ConsumerRecord<String, String> record)
        throws JsonProcessingException {

        Event event = this.consumer.readCommand(PROCESS_PAYMENT_COMMAND,
                                                PAYMENT_PROCESSED_EVENT,
                                                new ProcessPaymentCommand(
                                                    null,
                                                    null,
                                                    null,
                                                    null,
                                                    0.0d,
                                                    0),record);
        this.producer.sendEvent(PAYMENT_PROCESSED_EVENT, event);
        return event;
    }

    @Override//7
    public Event publishApproveOrderCommand(ConsumerRecord<String, String> record)
        throws JsonProcessingException {
        Event event = this.consumer.readCommand(APPROVE_ORDER_COMMAND,
                                                ORDER_APPROVED_EVENT,
                                                new ApproveOrderCommand(
                                                  null,
                                                  null,
                                                  null,
                                                  null,
                                                  0),record);
        this.producer.sendEvent(ORDER_APPROVED_EVENT, event);
        return event;
    }

    @Override//9
    public Event publishCancelProductReservationCommand(ConsumerRecord<String, String> record)
        throws JsonProcessingException {
        Event event = this.consumer.readCommand(CANCEL_PRODUCT_RESERVATION_COMMAND,
                                                PRODUCT_RESERVATION_CANCELED_EVENT,
                                                new ProductReservationCanselCommand(
                                                    null,
                                                    0,
                                                    null,
                                                    null,
                                                    null),record);
        this.producer.sendEvent(PRODUCT_RESERVATION_CANCELED_EVENT, event);
        return event;
    }

    @Override//11
    public Event publishCancelPaymentCommand(ConsumerRecord<String, String> record)
        throws JsonProcessingException {
        Event event = this.consumer.readCommand(CANCEL_PAYMENT_COMMAND,
                                                PAYMENT_CANCELED_EVENT,
                                                new CancelPaymentCommand(
                                                    null,
                                                    null,
                                                    null,
                                                    null,0),record);
        this.producer.sendEvent(PAYMENT_CANCELED_EVENT, event);
        return event;
    }

    @Override//13
    public Event publishRejectOrderCommand(ConsumerRecord<String, String> record)
        throws JsonProcessingException {
        Event event = this.consumer.readCommand(REJECT_ORDER_COMMAND,
                                                ORDER_REJECTED_EVENT,
                                                new RejectOrderCommand(
                                                    null,
                                                    null,
                                                    null,
                                                    null),record);
        this.producer.sendEvent(ORDER_REJECTED_EVENT, event);
        return event;
    }

    @Override
    public void testConsumer(ConsumerRecord<String, String> record) {
        this.consumer.consumeTest();
    }

    public void testProducer(){
        this.producer.sendTest();
    }

    public Properties getConsumerProps(){
        return this.consumer.getConsumerProperties();
    }

}
