package com.angel.saga.impl;

import com.angel.models.api.IEvent;
import com.angel.models.commands.*;
import com.angel.models.entities.Product;
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
    public Event eventFactory(Command cmd, String topic) {
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
                    .build();
            case PAYMENT_PROCESSED_EVENT:
                ProcessPaymentCommand paymentCmd = (ProcessPaymentCommand) cmd;
                return PaymentProcessedEvent.builder()
                    .orderId(paymentCmd.getOrderId())
                    .paymentId(paymentCmd.getPaymentId())
                    .paymentState(paymentCmd.getPaymentState())
                    .userId(paymentCmd.getUserId())
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
                    .build();
            case ORDER_APPROVED_EVENT:
                ApproveOrderCommand approvetCmd = (ApproveOrderCommand) cmd;
                return OrderApprovedEvent.builder()
                    .orderId(approvetCmd.getOrderId())
                    .state(approvetCmd.getState())
                    .userId(approvetCmd.getUserId())
                    .productId(approvetCmd.getProductId())
                    .quantity(approvetCmd.getQuantity())
                    .build();
            case PRODUCT_RESERVATION_CANCELED_EVENT:
                ProductReservationCancelCommand canceltCmd = (ProductReservationCancelCommand) cmd;
                return ProductReservationCanceledEvent.builder()
                    .orderId(canceltCmd.getOrderId())
                    .quantity(canceltCmd.getQuantity())
                    .userId(canceltCmd.getUserId())
                    .productId(canceltCmd.getProductId())
                    .reason(canceltCmd.getReason())
                    .build();
            case PAYMENT_CANCELED_EVENT:
                CancelPaymentCommand cancelPayment = (CancelPaymentCommand) cmd;
                return PaymentCanceledEvent.builder()
                    .orderId(cancelPayment.getOrderId())
                    .userId(cancelPayment.getUserId())
                    .paymentState(cancelPayment.getPaymentState())
                    .paymentId(cancelPayment.getPaymentId())
                    .productId(cancelPayment.getProductId())
                    .quantity(cancelPayment.getQuantity())
                    .build();
            case ORDER_REJECTED_EVENT:
                RejectOrderCommandProduct rejectCmd = (RejectOrderCommandProduct) cmd;
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
    public Command commandFactory(Event evt, String topic) {
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
                    .userId(createCmd.getUserId())
                    .build();
            case PROCESS_PAYMENT_COMMAND:
                ProductReservedEvent paymentCmd = (ProductReservedEvent) evt;
                return ProcessPaymentCommand.builder()
                    .orderId(paymentCmd.getOrderId())
                    .userId(paymentCmd.getUserId())
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
                    .build();
            case APPROVE_ORDER_COMMAND:
                PaymentProcessedEvent approvetCmd = (PaymentProcessedEvent) evt;
                return ApproveOrderCommand.builder()
                    .orderId(approvetCmd.getOrderId())
                    .userId(approvetCmd.getUserId())
                    .quantity(approvetCmd.getQuantity())
                    .productId(approvetCmd.getProductId())
                    .build();
            case CANCEL_PRODUCT_RESERVATION_COMMAND:
                ProductReservationCanceledEvent canceltCmd = (ProductReservationCanceledEvent) evt;
                return ProductReservationCancelCommand.builder()
                    .orderId(canceltCmd.getOrderId())
                    .quantity(canceltCmd.getQuantity())
                    .userId(canceltCmd.getUserId())
                    .productId(canceltCmd.getProductId())
                    .reason(canceltCmd.getReason())
                    .build();
            case CANCEL_PAYMENT_COMMAND:
                PaymentCanceledEvent cancelPayment = (PaymentCanceledEvent) evt;
                return CancelPaymentCommand.builder()
                    .orderId(cancelPayment.getOrderId())
                    .userId(cancelPayment.getUserId())
                    .paymentState(cancelPayment.getPaymentState())
                    .paymentId(cancelPayment.getPaymentId())
                    .productId(cancelPayment.getProductId())
                    .quantity(cancelPayment.getQuantity())
                    .build();
            case REJECT_ORDER_COMMAND_PAYMENT:
                PaymentCanceledEvent rejectCmd = (PaymentCanceledEvent) evt;
                return RejectOrderCommandProduct.builder()
                    .orderId(rejectCmd.getOrderId())
                    .reason("Not enpugh balance!")
                    .userId(rejectCmd.getUserId())
                    .productId(rejectCmd.getProductId())
                    .paymentId(rejectCmd.getPaymentId())
                    .build();

            case REJECT_ORDER_COMMAND_PRODUCT:
                ProductReservationCanceledEvent rejectEv = (ProductReservationCanceledEvent) evt;
                return RejectOrderCommandProduct.builder()
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
    public Command readEvent(String currentTopic, String nextTopicCommand, Event evt){
        Command createdCommand = commandFactory(evt, nextTopicCommand);
        return createdCommand;
    }

    //read command from topic, create and send event
    @Override
    public Event readCommand(String currentTopic, String nextTopicCommand, Command cmd){
        Event createdEvent = eventFactory(cmd, nextTopicCommand);
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

    @Override
    public Product createProduct() {
        return new Product();
    }

}
