package com.angel.kafkautils.producer;

import com.angel.models.commands.Command;

public interface IKafkaProducerConfig {
    void orderCreateCommand(final Command command);
    void reserveProductCommand();
    void processPaymentCommand();
    void approveOrderCommand();
    void cancelProductReservationCommand();
    void rejectOrderCommand();
}
