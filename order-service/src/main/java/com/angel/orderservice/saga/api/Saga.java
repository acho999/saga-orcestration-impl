package com.angel.orderservice.saga.api;

import com.angel.models.commands.*;
import com.angel.models.events.Event;
import com.angel.models.events.OrderApprovedEvent;
import com.angel.models.events.OrderCreatedEvent;
import com.angel.models.events.OrderRejectedEvent;


public interface Saga {

    Command handleOrderApprovedEvent(OrderApprovedEvent message);
    Command handleOrderCreatedEvent(OrderCreatedEvent message);
    Command handleOrderRejectedEvent(OrderRejectedEvent message);
    Event handleReserveProductCommand(ReserveProductCommand message);
    Event handleProcessPaymentCommand(ProcessPaymentCommand message);
    Event handleApproveOrderCommand(ApproveOrderCommand message);
    Event handleCancelProductReservationCommand(ProductReservationCancelCommand message);
    Event handleCancelPaymentCommand(CancelPaymentCommand message);
    Event handleRejectOrderCommandPayment(RejectOrderCommandPayment message);
    Event handleRejectOrderCommandProduct(RejectOrderCommandProduct message);

}
