package com.angel.saga.impl;

import com.angel.kafkautils.consumer.IKafkaConsumerConfig;
import com.angel.kafkautils.producer.IKafkaProducerConfig;
import com.angel.saga.api.Saga;
import com.angel.models.commands.Command;
import org.springframework.beans.factory.annotation.Autowired;

public class SagaImpl implements Saga {

    @Autowired
    private IKafkaProducerConfig producer;

    @Autowired
    private IKafkaConsumerConfig consumer;

    @Override
    public boolean handleOrderApprovedEvent() {
        this.consumer.orderAprovedEvent();
        return false;
    }

    @Override
    public boolean handleOrderCreatedEvent() {
        this.consumer.orderCreatedEvent();
        return false;
    }

    @Override
    public boolean handlePaymentProcessedEvent() {
        this.consumer.paymentProcessedEvent();
        return false;
    }

    @Override
    public boolean handleProductReservationCanceledEvent() {
        this.consumer.productReservationCanceledEvent();
        return false;
    }

    @Override
    public boolean handleProductReservedEvent() {
        this.consumer.productReservedEvent();
        return false;
    }

    @Override
    public boolean handleOrderRejectedEvent() {
        this.consumer.orderRejectedEvent();
        return false;
    }

    //------------------------------------------------------------------------------------------------


    @Override//1
    public boolean publishCreateOrderCommand(Command command) {
        this.producer.orderCreateCommand(command);
        return true;
    }

    @Override
    public boolean publishReserveProductCommand() {
        this.producer.reserveProductCommand();
        return true;
    }

    @Override
    public boolean publishProcessPaymentCommand() {
        this.producer.processPaymentCommand();
        return true;
    }

    @Override
    public boolean publishApproveOrderCommand() {
        this.producer.approveOrderCommand();
        return true;
    }

    @Override
    public boolean publishCancelProductReservationCommand() {
        this.producer.cancelProductReservationCommand();
        return true;
    }

    @Override
    public boolean publishRejectOrderCommand() {
        this.producer.rejectOrderCommand();
        return true;
    }

}
