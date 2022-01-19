package com.angel.orderservice.saga.api;

import com.angel.models.commands.*;
import com.angel.models.events.OrderApprovedEvent;
import com.angel.models.events.OrderCreatedEvent;
import com.angel.models.events.OrderRejectedEvent;
import com.angel.models.events.PaymentCanceledEvent;
import com.angel.models.events.PaymentProcessedEvent;
import com.angel.models.events.ProductReservationCanceledEvent;
import com.angel.models.events.ProductReservedEvent;


public interface Saga {
    Command handleOrderCreatedEvent(OrderCreatedEvent message);
    Command handleProductReservedEvent(ProductReservedEvent event);
    Command handlePaymentProcessedEvent(PaymentProcessedEvent event);
    void handleOrderApprovedEvent(OrderApprovedEvent message);
    Command handleProductReservationCanceledEvent(ProductReservationCanceledEvent event);
    Command handlePaymentCanceledEvent(PaymentCanceledEvent message);
    void handleOrderRejectedEvent(OrderRejectedEvent message);
}
