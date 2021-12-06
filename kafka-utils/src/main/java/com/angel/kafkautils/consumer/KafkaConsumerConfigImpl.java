package com.angel.kafkautils.consumer;

import com.angel.kafkautils.utils.Helpers;
import com.angel.models.events.OrderApprovedEvent;
import com.angel.models.events.OrderCreatedEvent;
import com.angel.models.events.OrderRejectedEvent;
import com.angel.models.events.PaymentProcessedEvent;
import com.angel.models.events.ProductReservationCalseledEvent;
import com.angel.models.events.ProductReservedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import static com.angel.kafkautils.constants.TopicConstants.*;

//consume events produce command
public class KafkaConsumerConfigImpl implements IKafkaConsumerConfig{

    @Autowired
    private Helpers helpers;

    @Override//2
    public void orderCreatedEvent() {

        this.helpers.produceCommand(CREATE_ORDER, RESERVE_PRODUCT, new OrderCreatedEvent(null,
                                                                           null,
                                                                           null,
                                                                           0,
                                                                           null));

    }

    @Override//4
    public void productReservedEvent() {

        this.helpers.produceCommand(RESERVE_PRODUCT, PROCESS_PAYMENT, new ProductReservedEvent(null,
                                                                                               null,
                                                                                               null ,
                                                                                               0));

    }

    @Override//6
    public void paymentProcessedEvent() {

        this.helpers.produceCommand(PROCESS_PAYMENT, APPROVE_ORDER, new PaymentProcessedEvent(null,
                                                                                              null,
                                                                                              null,
                                                                                              null));

    }

    @Override//8???
    public void orderAprovedEvent() {

        this.helpers.produceCommand(APPROVE_ORDER, APPROVE_ORDER, new OrderApprovedEvent(null,
                                                                                         null,
                                                                                         null));

    }

    @Override//10
    public void productReservationCanceledEvent() {

        this.helpers.produceCommand(PRODUCT_RESERVATION_CANCELED, ORDER_REJECTED, new ProductReservationCalseledEvent(null,
                                                                                                                      0,
                                                                                                                      null,
                                                                                                                      null,
                                                                                                                      null));

    }

    @Override//12
    public void orderRejectedEvent() {

        this.helpers.produceCommand(PROCESS_PAYMENT, APPROVE_ORDER, new OrderRejectedEvent(null,
                                                                                           null,
                                                                                           null));

    }

}
