package productInventoryservice.sagaAgregate.impl;

import com.angel.models.commands.*;
import com.angel.models.events.*;
import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import productInventoryservice.sagaAgregate.api.SagaAgregate;
import productInventoryservice.services.api.ProductInventoryService;

import static com.angel.models.constants.TopicConstants.*;

public class SagaAgregateImpl implements SagaAgregate {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SendMessage sendService;

    @Autowired
    private ProductInventoryService service;

    @Value(value = "${kafka.groupId}")
    private String group;

    private static final String droupId = "newApp11";

    @Autowired
    private Factory factory;

    @Override//10
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Command handleProductReservationCanceledEvent(String message)
        throws JsonProcessingException {
        Command command = this.factory.readEvent(PRODUCT_RESERVATION_CANCELED_EVENT,REJECT_ORDER_COMMAND,
                                                 new ProductReservationCalseledEvent(
                                                     null,
                                                     0,
                                                     null,
                                                     null,
                                                     null),message);

        RejectOrderCommand rejCommand =
            (RejectOrderCommand) this.handleProductReservationCanceledEvent(message);//10
        if (command != null) {
            service.resetQuantity(rejCommand.getProductId());
        }
        this.sendService.sendMessage(REJECT_ORDER_COMMAND, command, this.mapper);
        return command;
    }

    @Override//4
    @KafkaListener(topics = "topicName", groupId = droupId)
    public Command handleProductReservedEvent(String message)
        throws JsonProcessingException {

        Command command = this.factory.readEvent(PRODUCT_RESERVED_EVENT,PROCESS_PAYMENT_COMMAND,
                                                 new ProductReservedEvent(
                                                     null,
                                                     null,
                                                     null,
                                                     0,
                                                     0.0d),message);

        ReserveProductCommand event =
            (ReserveProductCommand) this.handleProductReservedEvent(message);//4

        if (event != null && !service.isAvailable(event.getProductId(), event.getQuantity())) {
            this.publishCancelProductReservationCommand(message);//9
            this.publishCancelPaymentCommand(message);//11
        }

        if (event != null) {
            service.isAvailable(event.getProductId(), event.getQuantity());
        }
        this.sendService.sendMessage(PROCESS_PAYMENT_COMMAND, command, this.mapper);
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
