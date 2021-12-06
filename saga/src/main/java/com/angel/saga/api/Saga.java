package com.angel.saga.api;

import com.angel.models.events.Event;
import com.angel.models.commands.Command;

public interface Saga {

    boolean handleOrderApprovedEvent();
    boolean handleOrderCreatedEvent();
    boolean handlePaymentProcessedEvent();
    boolean handleProductReservationCanceledEvent();
    boolean handleProductReservedEvent();
    boolean handleOrderRejectedEvent();

    boolean publishCreateOrderCommand(Command command);
    boolean publishReserveProductCommand();
    boolean publishProcessPaymentCommand();
    boolean publishApproveOrderCommand();
    boolean publishCancelProductReservationCommand();
    boolean publishRejectOrderCommand();

}
