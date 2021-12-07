package com.angel.kafkautils.producer;

import com.angel.kafkautils.utils.Helpers;
import com.angel.models.commands.ApproveOrderCommand;
import com.angel.models.commands.CancelPaymentCommand;
import com.angel.models.commands.ProcessPaymentCommand;
import com.angel.models.commands.ProductReservationCanselCommand;
import com.angel.models.commands.RejectOrderCommand;
import com.angel.models.commands.ReserveProductCommand;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.angel.models.commands.Command;
import com.angel.models.events.Event;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;

import static com.angel.kafkautils.constants.TopicConstants.APPROVE_ORDER;
import static com.angel.kafkautils.constants.TopicConstants.CREATE_ORDER;
import static com.angel.kafkautils.constants.TopicConstants.ORDER_REJECTED;
import static com.angel.kafkautils.constants.TopicConstants.PAYMENT_CANCELED;
import static com.angel.kafkautils.constants.TopicConstants.PROCESS_PAYMENT;
import static com.angel.kafkautils.constants.TopicConstants.PRODUCT_RESERVATION_CANCELED;
import static com.angel.kafkautils.constants.TopicConstants.RESERVE_PRODUCT;

//consume commands and produce next events
public class KafkaProducerConfigImpl implements IKafkaProducerConfig {

    @Autowired
    private Helpers helpers;

    @Override//1
    public Event createOrderCommand(final Command command){

        Producer<String, String> producer = new KafkaProducer<>(this.helpers.getProducerProperties());

        ObjectNode commandJson = JsonNodeFactory.instance.objectNode();

        Event createdEvent = this.helpers.eventFactory(command,CREATE_ORDER);

        commandJson.put(createdEvent.getClass().getSimpleName(), createdEvent.toString());

        producer.send(new ProducerRecord<>(CREATE_ORDER, command.getUserId(), commandJson.toString()));

        producer.close();

        return createdEvent;
    }

    @Override//3
    public Event reserveProductCommand() {

        return this.helpers.produceEvent(RESERVE_PRODUCT, RESERVE_PRODUCT, new ReserveProductCommand(null,
                                                                                              null,
                                                                                              null,
                                                                                              0));

    }

    @Override//5
    public Event processPaymentCommand() {
        return this.helpers.produceEvent(PROCESS_PAYMENT, PROCESS_PAYMENT, new ProcessPaymentCommand(null,
                                                                                              null,
                                                                                              null,
                                                                                              null,
                                                                                                   0.0d));
    }

    @Override//7
    public Event approveOrderCommand() {
        return this.helpers.produceEvent(APPROVE_ORDER, APPROVE_ORDER, new ApproveOrderCommand(null,
                                                                                        null,
                                                                                        null));

    }

    @Override//9
    public Event cancelProductReservationCommand() {
        return this.helpers.produceEvent(PRODUCT_RESERVATION_CANCELED, ORDER_REJECTED, new ProductReservationCanselCommand(null,
                                                                                                    0,
                                                                                                    null,
                                                                                                    null,
                                                                                                    null));
    }

    @Override//11
    public Event cancelPaymentCommand() {
        return this.helpers.produceEvent(PAYMENT_CANCELED, ORDER_REJECTED, new CancelPaymentCommand(null,
                                                                                                   null,
                                                                                                   null,
                                                                                                   null,
                                                                                                   0));
    }

    @Override
    public Event rejectOrderCommand() {//13
        return this.helpers.produceEvent(ORDER_REJECTED, ORDER_REJECTED, new RejectOrderCommand(null,
                                                                                       null,
                                                                                       null,
                                                                                                null));
    }

}
