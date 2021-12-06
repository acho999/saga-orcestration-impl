package com.angel.kafkautils.producer;

import com.angel.kafkautils.utils.Helpers;
import com.angel.models.commands.ApproveOrderCommand;
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
import static com.angel.kafkautils.constants.TopicConstants.PROCESS_PAYMENT;
import static com.angel.kafkautils.constants.TopicConstants.RESERVE_PRODUCT;

//consume commands and produce events
public class KafkaProducerConfigImpl implements IKafkaProducerConfig {

    @Autowired
    private Helpers helpers;

    @Override//1
    public void orderCreateCommand(final Command command){

        Producer<String, String> producer = new KafkaProducer<>(this.helpers.getProducerProperties());

        ObjectNode commandJson = JsonNodeFactory.instance.objectNode();

        Event createdEvent = this.helpers.eventFactory(command,CREATE_ORDER);

        commandJson.put(createdEvent.getClass().getSimpleName(), createdEvent.toString());

        producer.send(new ProducerRecord<>(CREATE_ORDER, command.getUserId(), commandJson.toString()));

        producer.close();

    }

    @Override//3
    public void reserveProductCommand() {

        this.helpers.produceEvent(RESERVE_PRODUCT, PROCESS_PAYMENT, new ReserveProductCommand(null,
                                                                                              null,
                                                                                              null,
                                                                                              0));

    }

    @Override//5
    public void processPaymentCommand() {
        this.helpers.produceEvent(RESERVE_PRODUCT, APPROVE_ORDER, new ProcessPaymentCommand(null,
                                                                                              null,
                                                                                              null,
                                                                                              null));
    }

    @Override//7
    public void approveOrderCommand() {
        this.helpers.produceEvent(APPROVE_ORDER, APPROVE_ORDER, new ApproveOrderCommand(null,
                                                                                        null,
                                                                                        null));

    }

    @Override//9
    public void cancelProductReservationCommand() {
        this.helpers.produceEvent(APPROVE_ORDER, APPROVE_ORDER, new ProductReservationCanselCommand(null,
                                                                                                    0,
                                                                                                    null,
                                                                                                    null,
                                                                                                    null));
    }

    @Override
    public void rejectOrderCommand() {//11
        this.helpers.produceEvent(APPROVE_ORDER, APPROVE_ORDER, new RejectOrderCommand(null,
                                                                                       null,
                                                                                       null));
    }

}
