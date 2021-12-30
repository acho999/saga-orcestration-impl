package com.angel.saga.impl;

import com.angel.models.api.IEvent;
import com.angel.models.commands.*;
import com.angel.models.events.*;
import com.angel.saga.api.Factory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

import static com.angel.models.constants.TopicConstants.*;

@Component
public class FactoryImpl implements Factory {

    private final ObjectMapper mapper;
    private Event event = null;
    private Command command = null;
    private final Logger logger;

    public FactoryImpl() {
        this.mapper = new ObjectMapper();
        this.logger = Logger.getLogger("KafkaConsumerConfigImpl");
    }

    @Override
    public synchronized Event eventFactory(Command cmd, String topic) {
        if (cmd == null) {
            return null;
        }
        switch (topic) {//1
            case ORDER_CREATED_EVENT:
                CreateOrderCommand createCmd = (CreateOrderCommand) cmd;
                return OrderCreatedEvent.builder()
                    .orderId(createCmd.getOrderId())
                    .productId(createCmd.getProductId())
                    .quantity(createCmd.getQuantity())
                    .state(createCmd.getState())
                    .userId(createCmd.getUserId())
                    .price(createCmd.getPrice())
                    .build();
            case PAYMENT_PROCESSED_EVENT:
                ProcessPaymentCommand paymentCmd = (ProcessPaymentCommand) cmd;
                return PaymentProcessedEvent.builder()
                    .orderId(paymentCmd.getOrderId())
                    .paymentId(paymentCmd.getPaymentId())
                    .paymentState(paymentCmd.getPaymentState())
                    .userId(paymentCmd.getUserId())
                    .price(paymentCmd.getPrice())
                    .quantity(paymentCmd.getQuantity())
                    .productId(paymentCmd.getProductId())
                    .build();
            case PRODUCT_RESERVED_EVENT:
                ReserveProductCommand reserveCmd = (ReserveProductCommand) cmd;
                return ProductReservedEvent.builder()
                    .orderId(reserveCmd.getOrderId())
                    .userId(reserveCmd.getUserId())
                    .productId(reserveCmd.getProductId())
                    .quantity(reserveCmd.getQuantity())
                    .price(reserveCmd.getPrice())
                    .build();
            case ORDER_APPROVED_EVENT:
                ApproveOrderCommand approvetCmd = (ApproveOrderCommand) cmd;
                return OrderApprovedEvent.builder()
                    .orderId(approvetCmd.getOrderId())
                    .state(approvetCmd.getState())
                    .userId(approvetCmd.getUserId())
                    .productId(approvetCmd.getProductId())
                    .quantity(approvetCmd.getQuantity())
                    .price(approvetCmd.getPrice())
                    .build();
            case PRODUCT_RESERVATION_CANCELED_EVENT:
                ProductReservationCancelCommand canceltCmd = (ProductReservationCancelCommand) cmd;
                return ProductReservationCalseledEvent.builder()
                    .orderId(canceltCmd.getOrderId())
                    .quantity(canceltCmd.getQuantity())
                    .userId(canceltCmd.getUserId())
                    .productId(canceltCmd.getProductId())
                    .reason(canceltCmd.getReason())
                    .price(canceltCmd.getPrice())
                    .build();
            case PAYMENT_CANCELED_EVENT:
                CancelPaymentCommand cancelPayment = (CancelPaymentCommand) cmd;
                return PaymentCanceledEvent.builder()
                    .orderId(cancelPayment.getOrderId())
                    .userId(cancelPayment.getUserId())
                    .price(cancelPayment.getPrice())
                    .paymentState(cancelPayment.getPaymentState())
                    .paymentId(cancelPayment.getPaymentId())
                    .productId(cancelPayment.getProductId())
                    .quantity(cancelPayment.getQuantity())
                    .build();
            case ORDER_REJECTED_EVENT:
                RejectOrderCommand rejectCmd = (RejectOrderCommand) cmd;
                return OrderRejectedEvent.builder()
                    .orderId(rejectCmd.getOrderId())
                    .reason(rejectCmd.getReason())
                    .userId(rejectCmd.getUserId())
                    .build();

            default:
                return null;
        }
    }

    @Override
    public synchronized Command commandFactory(Event evt, String topic) {
        if (evt == null) {
            return null;
        }

        switch (topic) {
            case CREATE_ORDER_COMMAND:
                OrderCreatedEvent createCmd = (OrderCreatedEvent) evt;
                return CreateOrderCommand.builder()
                    .orderId(createCmd.getOrderId())
                    .productId(createCmd.getProductId())
                    .quantity(createCmd.getQuantity())
                    .state(createCmd.getState())
                    .price(createCmd.getPrice())
                    .userId(createCmd.getUserId())
                    .build();
            case PROCESS_PAYMENT_COMMAND:
                ProductReservedEvent paymentCmd = (ProductReservedEvent) evt;
                return ProcessPaymentCommand.builder()
                    .orderId(paymentCmd.getOrderId())
                    .userId(paymentCmd.getUserId())
                    .price(paymentCmd.getPrice())
                    .quantity(paymentCmd.getQuantity())
                    .paymentId(null)
                    .paymentState(null)
                    .productId(paymentCmd.getProductId())
                    .build();
            case RESERVE_PRODUCT_COMMAND:
                OrderCreatedEvent reserveCmd = (OrderCreatedEvent) evt;
                return ReserveProductCommand.builder()
                    .orderId(reserveCmd.getOrderId())
                    .userId(reserveCmd.getUserId())
                    .productId(reserveCmd.getProductId())
                    .quantity(reserveCmd.getQuantity())
                    .price(reserveCmd.getPrice())
                    .build();
            case APPROVE_ORDER_COMMAND:
                PaymentProcessedEvent approvetCmd = (PaymentProcessedEvent) evt;
                return ApproveOrderCommand.builder()
                    .orderId(approvetCmd.getOrderId())
                    .userId(approvetCmd.getUserId())
                    .quantity(approvetCmd.getQuantity())
                    .productId(approvetCmd.getProductId())
                    .price(approvetCmd.getPrice())
                    .build();
            case CANCEL_PRODUCT_RESERVATION_COMMAND:
                ProductReservationCalseledEvent canceltCmd = (ProductReservationCalseledEvent) evt;
                return ProductReservationCancelCommand.builder()
                    .orderId(canceltCmd.getOrderId())
                    .quantity(canceltCmd.getQuantity())
                    .userId(canceltCmd.getUserId())
                    .productId(canceltCmd.getProductId())
                    .reason(canceltCmd.getReason())
                    .price(canceltCmd.getPrice())
                    .build();
            case CANCEL_PAYMENT_COMMAND:
                PaymentCanceledEvent cancelPayment = (PaymentCanceledEvent) evt;
                return CancelPaymentCommand.builder()
                    .orderId(cancelPayment.getOrderId())
                    .userId(cancelPayment.getUserId())
                    .price(cancelPayment.getPrice())
                    .paymentState(cancelPayment.getPaymentState())
                    .paymentId(cancelPayment.getPaymentId())
                    .productId(cancelPayment.getProductId())
                    .quantity(cancelPayment.getQuantity())
                    .price(cancelPayment.getPrice())
                    .build();
            case REJECT_ORDER_COMMAND_PAYMENT:
                PaymentCanceledEvent rejectCmd = (PaymentCanceledEvent) evt;
                return RejectOrderCommand.builder()
                    .orderId(rejectCmd.getOrderId())
                    .reason("Not enpugh balance!")
                    .userId(rejectCmd.getUserId())
                    .productId(rejectCmd.getProductId())
                    .paymentId(rejectCmd.getPaymentId())
                    .build();

            case REJECT_ORDER_COMMAND_PRODUCT:
                ProductReservationCalseledEvent rejectEv = (ProductReservationCalseledEvent) evt;
                return RejectOrderCommand.builder()
                    .orderId(rejectEv.getOrderId())
                    .reason("Not enpugh quantity!")
                    .userId(rejectEv.getUserId())
                    .productId(rejectEv.getProductId())
                    .paymentId(rejectEv.getPaymentId())
                    .build();

            default:
                return null;
        }

    }

    @Override
    public synchronized Command readEvent(String currentTopic, String nextTopicCommand, Event evt,
                             String message)
        throws JsonProcessingException {
        JsonNode actualObj = this.mapper.readTree(message);
        this.event = this.mapper.readValue(actualObj.toString(), evt.getClass());
        Command createdCommand = commandFactory(this.event, nextTopicCommand);
        return createdCommand;
    }

    //read command from topic, create and send event
    @Override
    public synchronized Event readCommand(String currentTopic, String nextTopicCommand, Command cmd,
                             String message)
        throws JsonProcessingException {

        JsonNode actualObj = this.mapper.readTree(message);
        this.command = this.mapper.readValue(actualObj.toString(), cmd.getClass());

        Event createdEvent = eventFactory(this.command, nextTopicCommand);
        return createdEvent;
    }

    private String convertObjToJson(String message, IEvent evt) throws JsonProcessingException {
        JsonNode actualObj = this.mapper.readTree(message);
        String classname = evt.getClass().getSimpleName();
        JsonNode obj = actualObj.get(classname);
        String actual = obj.toString().replaceAll("\\\\", "")
                                        .replaceAll("^\"|\"$", "");
        return actual;
    }

    @Override
    public JsonNode convertJsonToJsonNode(String message)
        throws JsonProcessingException {
        JsonNode actualObj = this.mapper.readTree(message);
        return actualObj;
    }

}
