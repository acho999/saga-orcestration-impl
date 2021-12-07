package com.angel.kafkautils.consumer;


import com.angel.models.commands.Command;

public interface IKafkaConsumerConfig {

    //void processEvent(final Event event, String topic);
    Command orderCreatedEvent();
    Command productReservedEvent();
    Command paymentProcessedEvent();
    Command orderAprovedEvent();
    Command productReservationCanceledEvent();
    Command orderRejectedEvent();


}
