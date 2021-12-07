package com.angel.kafkautils.consumer;

import com.angel.kafkautils.utils.Helpers;
import com.angel.models.commands.Command;
import com.angel.models.events.OrderApprovedEvent;
import com.angel.models.events.OrderCreatedEvent;
import com.angel.models.events.OrderRejectedEvent;
import com.angel.models.events.PaymentCanceledEvent;
import com.angel.models.events.PaymentProcessedEvent;
import com.angel.models.events.ProductReservationCalseledEvent;
import com.angel.models.events.ProductReservedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import static com.angel.kafkautils.constants.TopicConstants.*;

//consume events produce next commands
public class KafkaConsumerConfigImpl implements IKafkaConsumerConfig{

    @Autowired
    private Helpers helpers;

    @Override//2
    public Command orderCreatedEvent() {

        return this.helpers.produceCommand(CREATE_ORDER, RESERVE_PRODUCT, new OrderCreatedEvent(null,
                                                                           null,
                                                                           null,
                                                                           0,
                                                                           null));

    }

    @Override//4
    public Command productReservedEvent() {

        return this.helpers.produceCommand(RESERVE_PRODUCT, PROCESS_PAYMENT, new ProductReservedEvent(null,
                                                                                               null,
                                                                                               null ,
                                                                                               0));

    }

    @Override//6
    public Command paymentProcessedEvent() {

        return this.helpers.produceCommand(PROCESS_PAYMENT, APPROVE_ORDER, new PaymentProcessedEvent(null,
                                                                                              null,
                                                                                              null,
                                                                                              null,
                                                                                                     0));

    }

    @Override//8
    public Command orderAprovedEvent() {

        return this.helpers.produceCommand(APPROVE_ORDER, APPROVE_ORDER, new OrderApprovedEvent(null,
                                                                                         null,
                                                                                         null,
                                                                                                null,
                                                                                                0));

    }

    @Override//10
    public Command productReservationCanceledEvent() {

        return this.helpers.produceCommand(PRODUCT_RESERVATION_CANCELED, PRODUCT_RESERVATION_CANCELED, new ProductReservationCalseledEvent(null,
                                                                                                                      0,
                                                                                                                      null,
                                                                                                                      null,
                                                                                                                      null));

    }

    @Override//12
    public Command paymentCanceledEvent() {
        return this.helpers.produceCommand(PAYMENT_CANCELED, PAYMENT_CANCELED, new PaymentCanceledEvent(null,
                                                                                                        null,
                                                                                                        null,
                                                                                                        null,
                                                                                                        0));
    }

    @Override//14
    public Command orderRejectedEvent() {

        return this.helpers.produceCommand(ORDER_REJECTED, ORDER_REJECTED, new OrderRejectedEvent(null,
                                                                                           null,
                                                                                           null,
                                                                                                  null));

    }

}
