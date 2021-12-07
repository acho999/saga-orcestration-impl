package com.angel.kafkautils.producer;

import com.angel.models.commands.Command;
import com.angel.models.events.Event;

public interface IKafkaProducerConfig {
    Event createOrderCommand(final Command command);
    Event reserveProductCommand();
    Event processPaymentCommand();
    Event approveOrderCommand();
    Event cancelProductReservationCommand();
    Event cancelPaymentCommand();
    Event rejectOrderCommand();
}
