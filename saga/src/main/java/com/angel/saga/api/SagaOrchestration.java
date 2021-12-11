package com.angel.saga.api;

import com.angel.models.events.Event;
import com.angel.models.commands.Command;

public interface SagaOrchestration {

    Command handleOrderApprovedEvent();
    Command handleOrderCreatedEvent();
    Command handlePaymentProcessedEvent();
    Command handleProductReservationCanceledEvent();
    Command handleProductReservedEvent();
    Command handlePaymentCanceledEvent();
    Command handleOrderRejectedEvent();
    void testProducer();

    Event publishCreateOrderCommand(Command command);
    Event publishReserveProductCommand();
    Event publishProcessPaymentCommand();
    Event publishApproveOrderCommand();
    Event publishCancelProductReservationCommand();
    Event publishCancelPaymentCommand();
    Event publishRejectOrderCommand();
    void testConsumer();

}
