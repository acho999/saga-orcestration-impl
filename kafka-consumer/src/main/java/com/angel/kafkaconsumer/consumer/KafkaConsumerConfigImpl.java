package com.angel.kafkaconsumer.consumer;

import com.angel.models.commands.*;
import com.angel.models.events.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.connect.json.JsonDeserializer;
import org.apache.kafka.connect.json.JsonSerializer;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import static com.angel.models.constants.TopicConstants.*;

@Component
public class KafkaConsumerConfigImpl implements IKafkaConsumerConfig{

    private final Serializer<JsonNode> jsonSerializer;
    private final Deserializer<JsonNode> jsonDeserializer;
    private final Serde<JsonNode> jsonSerde;
    private final StreamsBuilder builder;
    private final ObjectMapper mapper;
    private Event event = null;
    private Command command = null;
    private Logger logger;

    public KafkaConsumerConfigImpl(){
        this.jsonSerializer = new JsonSerializer();
        this.jsonDeserializer = new JsonDeserializer();
        this.jsonSerde = Serdes.serdeFrom(jsonSerializer, jsonDeserializer);
        this.builder = new StreamsBuilder();
        this.mapper = new ObjectMapper();
        this.logger = Logger.getLogger("KafkaConsumerConfigImpl");
    }

    public Event eventFactory(Command cmd, String topic){
        if (cmd == null){
            return null;
        }
        switch (topic){
            case CREATE_ORDER_COMMAND:
                CreateOrderCommand createCmd = (CreateOrderCommand) cmd;
                return OrderCreatedEvent.builder()
                    .orderId(createCmd.getOrderId())
                    .productId(createCmd.getProductId())
                    .quantity(createCmd.getQuantity())
                    .state(createCmd.getState())
                    .build();
            case PROCESS_PAYMENT_COMMAND:
                ProcessPaymentCommand paymentCmd = (ProcessPaymentCommand) cmd;
                return PaymentProcessedEvent.builder()
                    .orderId(paymentCmd.getOrderId())
                    .paymentId(paymentCmd.getPaymentId())
                    .paymentState(paymentCmd.getPaymentState())
                    .userId(paymentCmd.getUserId())
                    .price(paymentCmd.getPrice())
                    .quantity(paymentCmd.getQuantity())
                    .build();
            case RESERVE_PRODUCT_COMMAND:
                ReserveProductCommand reserveCmd = (ReserveProductCommand) cmd;
                return ProductReservedEvent.builder()
                    .orderId(reserveCmd.getOrderId())
                    .userId(reserveCmd.getUserId())
                    .productId(reserveCmd.getProductId())
                    .quantity(reserveCmd.getQuantity())
                    .price(reserveCmd.getPrice())
                    .build();
            case APPROVE_ORDER_COMMAND:
                ApproveOrderCommand approvetCmd = (ApproveOrderCommand) cmd;
                return OrderApprovedEvent.builder()
                    .orderId(approvetCmd.getOrderId())
                    .state(approvetCmd.getState())
                    .userId(approvetCmd.getUserId())
                    .productId(approvetCmd.getProductId())
                    .productQuantity(approvetCmd.getProductQuantity())
                    .build();
            case CANCEL_PRODUCT_RESERVATION_COMMAND:
                ProductReservationCanselCommand canceltCmd = (ProductReservationCanselCommand) cmd;
                return ProductReservationCalseledEvent.builder()
                    .orderId(canceltCmd.getOrderId())
                    .quantity(canceltCmd.getQuantity())
                    .userId(canceltCmd.getUserId())
                    .productId(canceltCmd.getProductId())
                    .reason(canceltCmd.getReason())
                    .build();
            case CANCEL_PAYMENT_COMMAND:
                CancelPaymentCommand cancelPayment = (CancelPaymentCommand) cmd;
                return PaymentCanceledEvent.builder()
                    .orderId(cancelPayment.getOrderId())
                    .userId(cancelPayment.getUserId())
                    .amount(cancelPayment.getAmount())
                    .paymentState(cancelPayment.getPaymentState())
                    .paymentId(cancelPayment.getPaymentId())
                    .build();
            case REJECT_ORDER_COMMAND:
                RejectOrderCommand rejectCmd = (RejectOrderCommand) cmd;
                return OrderRejectedEvent.builder()
                    .orderId(rejectCmd.getOrderId())
                    .reason(rejectCmd.getReason())
                    .userId(rejectCmd.getUserId())
                    .build();

            default: return null;
        }
    }

    public Command commandFactory(Event evt, String topic){
        if (evt == null){
            return null;
        }

        switch (topic){
            case ORDER_CREATED_EVENT:
                OrderCreatedEvent createCmd = (OrderCreatedEvent) evt;
                return CreateOrderCommand.builder()
                    .orderId(createCmd.getOrderId())
                    .productId(createCmd.getProductId())
                    .quantity(createCmd.getQuantity())
                    .state(createCmd.getState())
                    .build();
            case PAYMENT_PROCESSED_EVENT:
                PaymentProcessedEvent paymentCmd = (PaymentProcessedEvent) evt;
                return ProcessPaymentCommand.builder()
                    .orderId(paymentCmd.getOrderId())
                    .paymentId(paymentCmd.getPaymentId())
                    .paymentState(paymentCmd.getPaymentState())
                    .userId(paymentCmd.getUserId())
                    .price(paymentCmd.getPrice())
                    .quantity(paymentCmd.getQuantity())
                    .build();
            case PRODUCT_RESERVED_EVENT:
                ProductReservedEvent reserveCmd = (ProductReservedEvent) evt;
                return ReserveProductCommand.builder()
                    .orderId(reserveCmd.getOrderId())
                    .userId(reserveCmd.getUserId())
                    .productId(reserveCmd.getProductId())
                    .quantity(reserveCmd.getQuantity())
                    .price(reserveCmd.getPrice())
                    .build();
            case ORDER_APPROVED_EVENT:
                OrderApprovedEvent approvetCmd = (OrderApprovedEvent) evt;
                return ApproveOrderCommand.builder()
                    .orderId(approvetCmd.getOrderId())
                    .state(approvetCmd.getState())
                    .userId(approvetCmd.getUserId())
                    .productQuantity(approvetCmd.getProductQuantity())
                    .productId(approvetCmd.getProductId())
                    .build();
            case PRODUCT_RESERVATION_CANCELED_EVENT:
                ProductReservationCalseledEvent canceltCmd = (ProductReservationCalseledEvent) evt;
                return ProductReservationCanselCommand.builder()
                    .orderId(canceltCmd.getOrderId())
                    .quantity(canceltCmd.getQuantity())
                    .userId(canceltCmd.getUserId())
                    .productId(canceltCmd.getProductId())
                    .reason(canceltCmd.getReason())
                    .build();
            case PAYMENT_CANCELED_EVENT:
                PaymentCanceledEvent cancelPayment = (PaymentCanceledEvent) evt;
                return CancelPaymentCommand.builder()
                    .orderId(cancelPayment.getOrderId())
                    .userId(cancelPayment.getUserId())
                    .amount(cancelPayment.getAmount())
                    .paymentState(cancelPayment.getPaymentState())
                    .paymentId(cancelPayment.getPaymentId())
                    .build();
            case ORDER_REJECTED_EVENT:
                OrderRejectedEvent rejectCmd = (OrderRejectedEvent) evt;
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


    //read event from topic, create and send next command
    @Override
    public Command readEvent(String currentTopic, String nextTopicCommand, Event evt) {

        KStream<String, JsonNode> eventJson = this.builder.stream(currentTopic,
                                                                  Consumed.with(Serdes.String(), this.jsonSerde));
        //here we read stream from orderCreate method and topic and we consume what is produced from producer
            this.logger.info(eventJson.toString());
            eventJson.foreach((key, value) -> {
                try {
                    this.event = this.mapper.readValue(value.traverse(), evt.getClass());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Command createdCommand = commandFactory(this.event, nextTopicCommand);
            return createdCommand;
    }


    //read command from topic, create and send event
    @Override
    public Event readCommand(String currentTopic, String nextTopicCommand, Command cmd) {
        KStream<String, JsonNode> eventJson = this.builder.stream(currentTopic,
                                                                  Consumed.with(Serdes.String(), this.jsonSerde));
        //here we read stream from BankTransactionProducer we consume what is produced from producer
        this.logger.info(eventJson.toString());
        eventJson.foreach((key, value)->{
            try {
                this.command = this.mapper.readValue(value.traverse(), cmd.getClass());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Event createdEvent = eventFactory(this.command,nextTopicCommand);
        return createdEvent;
    }

    public void consumeTest(){
        KStream<String, JsonNode> eventJson = this.builder.stream("testTopic",
                                                                  Consumed.with(Serdes.String(), this.jsonSerde));
        eventJson.foreach((key, value)->{

                this.logger.info(key.toString() + "  " + value.toString());

        });
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

}
