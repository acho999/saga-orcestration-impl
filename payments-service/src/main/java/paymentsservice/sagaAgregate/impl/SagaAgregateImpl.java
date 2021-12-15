package paymentsservice.sagaAgregate.impl;

import com.angel.models.commands.*;
import com.angel.models.events.*;
import com.angel.models.states.PaymentState;
import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import paymentsservice.models.Payment;
import paymentsservice.sagaAgregate.api.SagaAgregate;
import paymentsservice.services.api.PaymentsService;


import static com.angel.models.constants.TopicConstants.*;

public class SagaAgregateImpl implements SagaAgregate {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SendMessage sendService;

    @Autowired
    private PaymentsService paymentsService;

    @Value(value = "${kafka.groupId}")
    private String group;

    private static final String droupId = "newApp11";

    @Autowired
    private Factory factory;

    @Override//6
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Command handlePaymentProcessedEvent(String message)
        throws JsonProcessingException {

        ProcessPaymentCommand command =(ProcessPaymentCommand) this.factory.readEvent(PAYMENT_PROCESSED_EVENT,
                                                                                      APPROVE_ORDER_COMMAND,
                                                                                 new PaymentProcessedEvent(
                                                                                     null,
                                                                                     null,
                                                                                     null,
                                                                                     null,
                                                                                     0.0d,
                                                                                     0,
                                                                                     null),
                                                                                      message);
        if (command != null && !this.paymentsService.savePayment(command.getUserId(),
                                                                 new Payment(
                                                                     PaymentState.PAYMENT_APPROVED,
                                                                     (command.getPrice()
                                                                      * command.getQuantity())))) {
            this.publishCancelPaymentCommand(message);//11
            this.publishCancelProductReservationCommand(message);//9

        }
        this.sendService.sendMessage(APPROVE_ORDER_COMMAND, command, this.mapper);
        return command;
    }

    @Override//12
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Command handlePaymentCanceledEvent(String message)
        throws JsonProcessingException {

        Command command = this.factory.readEvent(PAYMENT_CANCELED_EVENT,REJECT_ORDER_COMMAND,
                                                 new PaymentCanceledEvent(
                                                     null,
                                                     null,
                                                     null,
                                                     null,
                                                     0), message);
        this.sendService.sendMessage(REJECT_ORDER_COMMAND, command, this.mapper);
        return command;
    }

    @Override//11
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Event publishCancelPaymentCommand(String message)
        throws JsonProcessingException {
        Event event = this.factory.readCommand(CANCEL_PAYMENT_COMMAND,
                                               PAYMENT_CANCELED_EVENT,
                                               new CancelPaymentCommand(
                                                   null,
                                                   null,
                                                   null,
                                                   null,0),message);
        this.sendService.sendMessage(PAYMENT_CANCELED_EVENT, event, this.mapper);
        return event;
    }

    @Override//9
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Event publishCancelProductReservationCommand(String message)
        throws JsonProcessingException {
        Event event = this.factory.readCommand(CANCEL_PRODUCT_RESERVATION_COMMAND,
                                               PRODUCT_RESERVATION_CANCELED_EVENT,
                                               new ProductReservationCanselCommand(
                                                   null,
                                                   0,
                                                   null,
                                                   null,
                                                   null),message);
        this.sendService.sendMessage(PRODUCT_RESERVATION_CANCELED_EVENT, event, this.mapper);
        return event;
    }
}
