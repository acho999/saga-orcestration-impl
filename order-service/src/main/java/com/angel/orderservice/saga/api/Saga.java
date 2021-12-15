package com.angel.orderservice.saga.api;

import com.angel.models.commands.Command;
import com.angel.models.events.Event;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Properties;

public interface Saga {

    Command handleOrderApprovedEvent(String message)
        throws JsonProcessingException;
    Command handleOrderCreatedEvent(String message)
        throws JsonProcessingException;
    Command handlePaymentProcessedEvent(String message)
        throws JsonProcessingException;
    Command handleProductReservationCanceledEvent(String message)
        throws JsonProcessingException;
    Command handleProductReservedEvent(String message)
        throws JsonProcessingException;
    Command handlePaymentCanceledEvent(String message)
        throws JsonProcessingException;
    Command handleOrderRejectedEvent(String message)
        throws JsonProcessingException;

    Event publishCreateOrderCommand(Command command, String message)
        throws JsonProcessingException;
    Event publishReserveProductCommand(String message)
        throws JsonProcessingException;
    Event publishProcessPaymentCommand(String message)
        throws JsonProcessingException;
    Event publishApproveOrderCommand(String message)
        throws JsonProcessingException;
    Event publishCancelProductReservationCommand(String message)
        throws JsonProcessingException;
    Event publishCancelPaymentCommand(String message)
        throws JsonProcessingException;
    Event publishRejectOrderCommand(String message)
        throws JsonProcessingException;

}
