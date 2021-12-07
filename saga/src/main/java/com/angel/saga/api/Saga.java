package com.angel.saga.api;

import com.angel.models.events.Event;
import com.angel.models.commands.Command;

public interface Saga {

    Command handleOrderApprovedEvent();
    Command handleOrderCreatedEvent();
    Command handlePaymentProcessedEvent();
    Command handleProductReservationCanceledEvent();
    Command handleProductReservedEvent();
    Command handleOrderRejectedEvent();

    Event publishCreateOrderCommand(Command command);
    Event publishReserveProductCommand();
    Event publishProcessPaymentCommand();
    Event publishApproveOrderCommand();
    Event publishCancelProductReservationCommand();
    Event publishRejectOrderCommand();

}
