package com.angel.saga.impl;

import com.angel.kafkautils.consumer.IKafkaConsumerConfig;
import com.angel.kafkautils.producer.IKafkaProducerConfig;
import com.angel.models.events.Event;
import com.angel.saga.api.SagaOrchestration;
import com.angel.models.commands.Command;
import org.springframework.beans.factory.annotation.Autowired;

public class SagaOrchestrationImpl implements SagaOrchestration {

    @Autowired
    private IKafkaProducerConfig producer;

    @Autowired
    private IKafkaConsumerConfig consumer;

    @Override//2
    public Command handleOrderCreatedEvent() {
        return this.consumer.orderCreatedEvent();
    }

    @Override//4
    public Command handleProductReservedEvent() {
        return this.consumer.productReservedEvent();
    }

    @Override//6
    public Command handlePaymentProcessedEvent() {
        return this.consumer.paymentProcessedEvent();
    }

    @Override//8
    public Command handleOrderApprovedEvent() {
        return this.consumer.orderAprovedEvent();
    }

    @Override//10
    public Command handleProductReservationCanceledEvent() {
        return this.consumer.productReservationCanceledEvent();
    }

    @Override//12
    public Command handlePaymentCanceledEvent() {
        return this.consumer.paymentCanceledEvent();
    }

    @Override//14
    public Command handleOrderRejectedEvent() {
        return this.consumer.orderRejectedEvent();
    }

    //------------------------------------------------------------------------------------------------


    @Override//1
    public Event publishCreateOrderCommand(Command command) {
        return this.producer.createOrderCommand(command);
    }

    @Override//3
    public Event publishReserveProductCommand() {
        return this.producer.reserveProductCommand();
    }

    @Override//5
    public Event publishProcessPaymentCommand() {
        return this.producer.processPaymentCommand();
    }

    @Override//7
    public Event publishApproveOrderCommand() {
        return this.producer.approveOrderCommand();
    }

    @Override//9
    public Event publishCancelProductReservationCommand() {
        return this.producer.cancelProductReservationCommand();
    }

    @Override//11
    public Event publishCancelPaymentCommand() {
        return this.producer.cancelPaymentCommand();
    }

    @Override//13
    public Event publishRejectOrderCommand() {
        return this.producer.rejectOrderCommand();
    }

}
