package com.angel.orderservice.saga.api;

import com.angel.models.commands.*;
import com.angel.models.events.OrderApprovedEvent;
import com.angel.models.events.OrderCreatedEvent;
import com.angel.models.events.OrderRejectedEvent;
import com.angel.models.events.PaymentCanceledEvent;
import com.angel.models.events.PaymentProcessedEvent;
import com.angel.models.events.ProductReservationCanceledEvent;
import com.angel.models.events.ProductReservedEvent;


public interface SagaOrchestrator {
    Command handleOrderCreatedEvent(OrderCreatedEvent event) throws InterruptedException;
    Command handleProductReservedEvent(ProductReservedEvent event) throws InterruptedException;
    Command handlePaymentProcessedEvent(PaymentProcessedEvent event);
    Boolean handleOrderApprovedEvent(OrderApprovedEvent event);
    Command handleProductReservationCanceledEvent(ProductReservationCanceledEvent event);
    Command handlePaymentCanceledEvent(PaymentCanceledEvent event);
    Boolean handleOrderRejectedEvent(OrderRejectedEvent event);
}
