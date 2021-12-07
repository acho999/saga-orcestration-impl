package com.angel.kafkautils.consumer;


import com.angel.models.commands.Command;
import com.angel.models.events.Event;

public interface IKafkaConsumerConfig {

    //void processEvent(final Event event, String topic);
    Command orderCreatedEvent();
    Command productReservedEvent();
    Command paymentProcessedEvent();
    Command orderAprovedEvent();
    Command productReservationCanceledEvent();
    Command paymentCanceledEvent();
    Command orderRejectedEvent();


}
