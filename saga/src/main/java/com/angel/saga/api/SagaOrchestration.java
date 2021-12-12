package com.angel.saga.api;

import com.angel.models.events.Event;
import com.angel.models.commands.Command;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.Properties;

public interface SagaOrchestration {

    Command handleOrderApprovedEvent(ConsumerRecord<String, String> record)
        throws JsonProcessingException;
    Command handleOrderCreatedEvent(ConsumerRecord<String, String> record)
        throws JsonProcessingException;
    Command handlePaymentProcessedEvent(ConsumerRecord<String, String> record)
        throws JsonProcessingException;
    Command handleProductReservationCanceledEvent(ConsumerRecord<String, String> record)
        throws JsonProcessingException;
    Command handleProductReservedEvent(ConsumerRecord<String, String> record)
        throws JsonProcessingException;
    Command handlePaymentCanceledEvent(ConsumerRecord<String, String> record)
        throws JsonProcessingException;
    Command handleOrderRejectedEvent(ConsumerRecord<String, String> record)
        throws JsonProcessingException;
    void testProducer();

    Event publishCreateOrderCommand(Command command, ConsumerRecord<String, String> record)
        throws JsonProcessingException;
    Event publishReserveProductCommand(ConsumerRecord<String, String> record)
        throws JsonProcessingException;
    Event publishProcessPaymentCommand(ConsumerRecord<String, String> record)
        throws JsonProcessingException;
    Event publishApproveOrderCommand(ConsumerRecord<String, String> record)
        throws JsonProcessingException;
    Event publishCancelProductReservationCommand(ConsumerRecord<String, String> record)
        throws JsonProcessingException;
    Event publishCancelPaymentCommand(ConsumerRecord<String, String> record)
        throws JsonProcessingException;
    Event publishRejectOrderCommand(ConsumerRecord<String, String> record)
        throws JsonProcessingException;
    void testConsumer(ConsumerRecord<String, String> record);
    Properties getConsumerProps();

}
