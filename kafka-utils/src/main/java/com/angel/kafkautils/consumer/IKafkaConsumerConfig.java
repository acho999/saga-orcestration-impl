package com.angel.kafkautils.consumer;


public interface IKafkaConsumerConfig {

    //void processEvent(final Event event, String topic);
    void orderCreatedEvent();
    void productReservedEvent();
    void paymentProcessedEvent();
    void orderAprovedEvent();
    void productReservationCanceledEvent();
    void orderRejectedEvent();


}
