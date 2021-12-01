package com.angel.saga.api;

import events.Event;
import commands.Command;
import java.util.Optional;

public interface Saga {

    Optional handleOrderApprovedEvent(Event event);
    Optional handleOrderCreatedEvent(Event event);
    Optional handlePaymentProcessedEvent(Event event);
    Optional handleProductReservationCanceledEvent(Event event);
    Optional handleProductReservedEvent(Event event);
    Optional handleOrderRejectedEvent(Event event);

    Optional publishCreateOrderCommand(Command command);
    Optional publishReserveProductCommand(Command command);
    Optional publishProcessPaymentCommand(Command command);
    Optional publishApproveOrderCommand(Command command);
    Optional publishCancelProductReservationCommand(Command command);
    Optional publishRejectOrderCommand(Command command);

}
