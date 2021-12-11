package com.angel.kafkaproducer.producer;

import com.angel.models.commands.ApproveOrderCommand;
import com.angel.models.commands.CancelPaymentCommand;
import com.angel.models.commands.Command;
import com.angel.models.commands.CreateOrderCommand;
import com.angel.models.commands.ProcessPaymentCommand;
import com.angel.models.commands.ProductReservationCanselCommand;
import com.angel.models.commands.RejectOrderCommand;
import com.angel.models.commands.ReserveProductCommand;
import com.angel.models.events.Event;
import com.angel.models.events.OrderApprovedEvent;
import com.angel.models.events.OrderCreatedEvent;
import com.angel.models.events.OrderRejectedEvent;
import com.angel.models.events.PaymentCanceledEvent;
import com.angel.models.events.PaymentProcessedEvent;
import com.angel.models.events.ProductReservationCalseledEvent;
import com.angel.models.events.ProductReservedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.stereotype.Component;
import java.util.Properties;
import java.util.logging.Logger;

import static com.angel.models.constants.TopicConstants.*;

@Component
public class KafkaProducerConfigImpl implements IKafkaProducerConfig {

    private final StreamsBuilder builder;
    private final ObjectMapper mapper;
    private Event event = null;
    private Command command = null;
    private Producer<String, String> producer;
    private ObjectNode commandJson;
    private Logger logger;

    public KafkaProducerConfigImpl(){
        this.builder = new StreamsBuilder();
        this.mapper = new ObjectMapper();
        this.producer = new KafkaProducer<>(getProducerProperties());
        this.commandJson = JsonNodeFactory.instance.objectNode();
        this.logger = Logger.getLogger("KafkaProducerConfigImpl");
    }

    public Event eventFactory(Command command, String topic){
        switch (topic){
            case CREATE_ORDER_COMMAND:
                CreateOrderCommand createCmd = (CreateOrderCommand) command;
                return OrderCreatedEvent.builder()
                    .orderId(createCmd.getOrderId())
                    .productId(createCmd.getProductId())
                    .quantity(createCmd.getQuantity())
                    .state(createCmd.getState())
                    .build();
            case PROCESS_PAYMENT_COMMAND:
                ProcessPaymentCommand paymentCmd = (ProcessPaymentCommand) command;
                return PaymentProcessedEvent.builder()
                    .orderId(paymentCmd.getOrderId())
                    .paymentId(paymentCmd.getPaymentId())
                    .paymentState(paymentCmd.getPaymentState())
                    .userId(paymentCmd.getUserId())
                    .build();
            case RESERVE_PRODUCT_COMMAND:
                ReserveProductCommand reserveCmd = (ReserveProductCommand) command;
                return ProductReservedEvent.builder()
                    .orderId(reserveCmd.getOrderId())
                    .userId(reserveCmd.getUserId())
                    .productId(reserveCmd.getProductId())
                    .quantity(reserveCmd.getQuantity())
                    .price(reserveCmd.getPrice())
                    .build();
            case APPROVE_ORDER_COMMAND:
                ApproveOrderCommand approvetCmd = (ApproveOrderCommand) command;
                return OrderApprovedEvent.builder()
                    .orderId(approvetCmd.getOrderId())
                    .state(approvetCmd.getState())
                    .userId(approvetCmd.getUserId())
                    .productId(approvetCmd.getProductId())
                    .productQuantity(approvetCmd.getProductQuantity())
                    .build();
            case CANCEL_PRODUCT_RESERVATION_COMMAND:
                ProductReservationCanselCommand canceltCmd = (ProductReservationCanselCommand) command;
                return ProductReservationCalseledEvent.builder()
                    .orderId(canceltCmd.getOrderId())
                    .quantity(canceltCmd.getQuantity())
                    .userId(canceltCmd.getUserId())
                    .productId(canceltCmd.getProductId())
                    .reason(canceltCmd.getReason())
                    .build();
            case CANCEL_PAYMENT_COMMAND:
                CancelPaymentCommand cancelPayment = (CancelPaymentCommand) command;
                return PaymentCanceledEvent.builder()
                    .orderId(cancelPayment.getOrderId())
                    .userId(cancelPayment.getUserId())
                    .amount(cancelPayment.getAmount())
                    .paymentState(cancelPayment.getPaymentState())
                    .paymentId(cancelPayment.getPaymentId())
                    .build();
            case REJECT_ORDER_COMMAND:
                RejectOrderCommand rejectCmd = (RejectOrderCommand) command;
                return OrderRejectedEvent.builder()
                    .orderId(rejectCmd.getOrderId())
                    .reason(rejectCmd.getReason())
                    .userId(rejectCmd.getUserId())
                    .build();

            default: return null;
        }
    }

    public Command commandFactory(Event event, String topic){
        switch (topic){
            case ORDER_CREATED_EVENT:
                OrderCreatedEvent createCmd = (OrderCreatedEvent) event;
                return CreateOrderCommand.builder()
                    .orderId(createCmd.getOrderId())
                    .productId(createCmd.getProductId())
                    .quantity(createCmd.getQuantity())
                    .state(createCmd.getState())
                    .build();
            case PAYMENT_PROCESSED_EVENT:
                PaymentProcessedEvent paymentCmd = (PaymentProcessedEvent) event;
                return ProcessPaymentCommand.builder()
                    .orderId(paymentCmd.getOrderId())
                    .paymentId(paymentCmd.getPaymentId())
                    .paymentState(paymentCmd.getPaymentState())
                    .userId(paymentCmd.getUserId())
                    .build();
            case PRODUCT_RESERVED_EVENT:
                ProductReservedEvent reserveCmd = (ProductReservedEvent) event;
                return ReserveProductCommand.builder()
                    .orderId(reserveCmd.getOrderId())
                    .userId(reserveCmd.getUserId())
                    .productId(reserveCmd.getProductId())
                    .quantity(reserveCmd.getQuantity())
                    .price(reserveCmd.getPrice())
                    .build();
            case ORDER_APPROVED_EVENT:
                OrderApprovedEvent approvetCmd = (OrderApprovedEvent) event;
                return ApproveOrderCommand.builder()
                    .orderId(approvetCmd.getOrderId())
                    .state(approvetCmd.getState())
                    .userId(approvetCmd.getUserId())
                    .productQuantity(approvetCmd.getProductQuantity())
                    .productId(approvetCmd.getProductId())
                    .build();
            case PRODUCT_RESERVATION_CANCELED_EVENT:
                ProductReservationCalseledEvent canceltCmd = (ProductReservationCalseledEvent) event;
                return ProductReservationCanselCommand.builder()
                    .orderId(canceltCmd.getOrderId())
                    .quantity(canceltCmd.getQuantity())
                    .userId(canceltCmd.getUserId())
                    .productId(canceltCmd.getProductId())
                    .reason(canceltCmd.getReason())
                    .build();
            case PAYMENT_CANCELED_EVENT:
                PaymentCanceledEvent cancelPayment = (PaymentCanceledEvent) event;
                return CancelPaymentCommand.builder()
                    .orderId(cancelPayment.getOrderId())
                    .userId(cancelPayment.getUserId())
                    .amount(cancelPayment.getAmount())
                    .paymentState(cancelPayment.getPaymentState())
                    .paymentId(cancelPayment.getPaymentId())
                    .build();
            case ORDER_REJECTED_EVENT:
                OrderRejectedEvent rejectCmd = (OrderRejectedEvent) event;
                return RejectOrderCommand.builder()
                    .orderId(rejectCmd.getOrderId())
                    .reason(rejectCmd.getReason())
                    .userId(rejectCmd.getUserId())
                    .build();

            default: return null;
        }

    }

    public Properties getProducerProperties(){

        Properties properties = new Properties();

        // kafka bootstrap server
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // producer acks
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all"); // strongest producing guarantee
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "3");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");
        // leverage idempotent producer from Kafka 0.11 !
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true"); // ensure we don't push duplicates

        return properties;
    }

    public void sendCommand(String nextTopicCommand, Command createdCommand){
        if (nextTopicCommand == null || createdCommand == null){
            return;
        }
        commandJson.put(createdCommand.getClass().getSimpleName(), createdCommand.toString());
        this.logger.info(commandJson.toString());

        producer.send(
            new ProducerRecord<>(nextTopicCommand, command.getUserId(), commandJson.toString()));

        producer.close();
    }

    public void sendEvent(String nextTopicCommand, Event createdEvent){
        if (nextTopicCommand == null || createdEvent == null){
            return;
        }
        commandJson.put(createdEvent.getClass().getSimpleName(), createdEvent.toString());
        this.logger.info(commandJson.toString());
        producer.send(
            new ProducerRecord<>(nextTopicCommand, command.getUserId(), commandJson.toString()));

        producer.close();
    }

    public Properties getConsumerProperties(){
        Properties config = new Properties();

        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "E-commerce-application");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9091");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        //here we do not config ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG and ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG
        //because above we use JSONSerde

        // we disable the cache to demonstrate all the "steps" involved in the transformation - not recommended in prod
        // Enable record cache of size 10 MB.
        //streamsConfiguration.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 10 * 1024 * 1024L);
        config.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0");

        // Exactly once processing!!
        config.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);

        return config;
    }

    public void sendTest(){
        commandJson.put("key", "sendTestProducer");
        this.logger.info(commandJson.toString());

        producer.send(
            new ProducerRecord<>("testTopic", "KEY", commandJson.toString()));
        producer.close();
    }
}
