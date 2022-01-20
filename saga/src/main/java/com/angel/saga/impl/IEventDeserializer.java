package com.angel.saga.impl;

import com.angel.models.api.IEvent;
import com.angel.models.commands.*;
import com.angel.models.events.*;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.util.SerializationUtils;


public class IEventDeserializer implements Deserializer<IEvent> {

    @Override
    public IEvent deserialize(String s, byte[] bytes) {

            String className = SerializationUtils.deserialize(bytes).getClass().getSimpleName();

            switch (className){

                case "CreateOrderCommand" : return (CreateOrderCommand) SerializationUtils.deserialize(bytes);
                case "ProcessPaymentCommand" : return (ProcessPaymentCommand) SerializationUtils.deserialize(bytes);
                case "ApproveOrderCommand" : return (ApproveOrderCommand) SerializationUtils.deserialize(bytes);
                case "CancelPaymentCommand" : return (CancelPaymentCommand) SerializationUtils.deserialize(bytes);
                case "ProductReservationCancelCommand" : return (ProductReservationCancelCommand) SerializationUtils.deserialize(bytes);
                case "RejectOrderCommandProduct" : return (RejectOrderCommandProduct) SerializationUtils.deserialize(bytes);
                case "ReserveProductCommand" : return (ReserveProductCommand) SerializationUtils.deserialize(bytes);
                case "OrderApprovedEvent" : return (OrderApprovedEvent) SerializationUtils.deserialize(bytes);
                case "OrderCreatedEvent" : return (OrderCreatedEvent) SerializationUtils.deserialize(bytes);
                case "OrderRejectedEvent" : return (OrderRejectedEvent) SerializationUtils.deserialize(bytes);
                case "PaymentProcessedEvent" : return (PaymentProcessedEvent) SerializationUtils.deserialize(bytes);
                case "ProductReservationCanceledEvent" : return (ProductReservationCanceledEvent) SerializationUtils.deserialize(bytes);
                case "ProductReservedEvent" : return (ProductReservedEvent) SerializationUtils.deserialize(bytes);
                case "PaymentCanceledEvent" : return (PaymentCanceledEvent) SerializationUtils.deserialize(bytes);

                default: return null;

            }

    }
}
