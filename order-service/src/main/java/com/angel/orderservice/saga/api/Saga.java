package com.angel.orderservice.saga.api;

import com.angel.models.commands.Command;
import com.angel.models.events.Event;
import com.fasterxml.jackson.core.JsonProcessingException;


public interface Saga {

    Command handleOrderApprovedEvent(String message)
        throws JsonProcessingException;
    Command handleOrderCreatedEvent(String message)
        throws JsonProcessingException;
    Command handleOrderRejectedEvent(String message)
        throws JsonProcessingException;
    Event handleCreateOrderCommand(String message)
        throws JsonProcessingException;
    Event handleReserveProductCommand(String message)
        throws JsonProcessingException;
    Event handleProcessPaymentCommand(String message)
        throws JsonProcessingException;
    Event handleApproveOrderCommand(String message)
        throws JsonProcessingException;
    Event handleCancelProductReservationCommand(String message)
        throws JsonProcessingException;
    Event handleCancelPaymentCommand(String message)
        throws JsonProcessingException;
    Event handleRejectOrderCommandPayment(String message)
        throws JsonProcessingException;
    Event handleRejectOrderCommandProduct(String message)
        throws JsonProcessingException;

}
