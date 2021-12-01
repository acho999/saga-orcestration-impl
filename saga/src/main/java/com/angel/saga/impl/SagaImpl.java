package com.angel.saga.impl;

import com.angel.kafkautils.producer.IKafkaProducerConfig;
import com.angel.saga.api.Saga;
import events.Event;
import commands.Command;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class SagaImpl implements Saga {

    @Autowired
    private IKafkaProducerConfig producer;

    @Override
    public Optional handleOrderApprovedEvent(Event event) {
        return Optional.empty();
    }

    @Override
    public Optional handleOrderCreatedEvent(Event event) {
        return Optional.empty();
    }

    @Override
    public Optional handlePaymentProcessedEvent(Event event) {
        return Optional.empty();
    }

    @Override
    public Optional handleProductReservationCanceledEvent(Event event) {
        return Optional.empty();
    }

    @Override
    public Optional handleProductReservedEvent(Event event) {
        return Optional.empty();
    }

    @Override
    public Optional handleOrderRejectedEvent(Event event) {
        return Optional.empty();
    }






    @Override
    public Optional publishCreateOrderCommand(Command command) {
        return Optional.empty();
    }

    @Override
    public Optional publishReserveProductCommand(Command command) {
        return Optional.empty();
    }

    @Override
    public Optional publishProcessPaymentCommand(Command command) {
        return Optional.empty();
    }

    @Override
    public Optional publishApproveOrderCommand(Command command) {
        return Optional.empty();
    }

    @Override
    public Optional publishCancelProductReservationCommand(Command command) {
        return Optional.empty();
    }

    @Override
    public Optional publishRejectOrderCommand(Command command) {
        return Optional.empty();
    }
}
