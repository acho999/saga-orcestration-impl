package com.angel.kafkautils.utils;

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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.connect.json.JsonDeserializer;
import org.apache.kafka.connect.json.JsonSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

import static com.angel.kafkautils.constants.TopicConstants.APPROVE_ORDER;
import static com.angel.kafkautils.constants.TopicConstants.CREATE_ORDER;
import static com.angel.kafkautils.constants.TopicConstants.ORDER_REJECTED;
import static com.angel.kafkautils.constants.TopicConstants.PAYMENT_CANCELED;
import static com.angel.kafkautils.constants.TopicConstants.PROCESS_PAYMENT;
import static com.angel.kafkautils.constants.TopicConstants.PRODUCT_RESERVATION_CANCELED;
import static com.angel.kafkautils.constants.TopicConstants.RESERVE_PRODUCT;

@Component
public class HelpersImpl implements Helpers{

    private final Serializer<JsonNode> jsonSerializer;
    private final Deserializer<JsonNode> jsonDeserializer;
    private final Serde<JsonNode> jsonSerde;
    private final StreamsBuilder builder;
    private final ObjectMapper mapper;
    private Event event = null;
    private Command command = null;
    private Producer<String, String> producer;
    private ObjectNode commandJson;

    public HelpersImpl(){
        this.jsonSerializer = new JsonSerializer();
        this.jsonDeserializer = new JsonDeserializer();
        this.jsonSerde = Serdes.serdeFrom(jsonSerializer, jsonDeserializer);
        this.builder = new StreamsBuilder();
        this.mapper = new ObjectMapper();
        this.producer = new KafkaProducer<>(getProducerProperties());
        this.commandJson = JsonNodeFactory.instance.objectNode();
    }

    public Event eventFactory(Command command, String topic){
        switch (topic){
            case CREATE_ORDER:
                CreateOrderCommand createCmd = (CreateOrderCommand) command;
                return OrderCreatedEvent.builder()
                    .orderId(createCmd.getOrderId())
                    .productId(createCmd.getProductId())
                    .quantity(createCmd.getQuantity())
                    .state(createCmd.getState())
                    .build();
            case PROCESS_PAYMENT:
                ProcessPaymentCommand paymentCmd = (ProcessPaymentCommand) command;
                return PaymentProcessedEvent.builder()
                    .orderId(paymentCmd.getOrderId())
                    .paymentId(paymentCmd.getPaymentId())
                    .paymentState(paymentCmd.getPaymentState())
                    .userId(paymentCmd.getUserId())
                    .build();
            case RESERVE_PRODUCT:
                ReserveProductCommand reserveCmd = (ReserveProductCommand) command;
                return ProductReservedEvent.builder()
                    .orderId(reserveCmd.getOrderId())
                    .userId(reserveCmd.getUserId())
                    .productId(reserveCmd.getProductId())
                    .quantity(reserveCmd.getQuantity())
                    .build();
            case APPROVE_ORDER:
                ApproveOrderCommand approvetCmd = (ApproveOrderCommand) command;
                return OrderApprovedEvent.builder()
                    .orderId(approvetCmd.getOrderId())
                    .state(approvetCmd.getState())
                    .userId(approvetCmd.getUserId())
                    .productId(approvetCmd.getProductId())
                    .productQuantity(approvetCmd.getProductQuantity())
                    .build();
            case PRODUCT_RESERVATION_CANCELED:
                ProductReservationCanselCommand canceltCmd = (ProductReservationCanselCommand) command;
                return ProductReservationCalseledEvent.builder()
                    .orderId(canceltCmd.getOrderId())
                    .quantity(canceltCmd.getQuantity())
                    .userId(canceltCmd.getUserId())
                    .productId(canceltCmd.getProductId())
                    .reason(canceltCmd.getReason())
                    .build();
            case PAYMENT_CANCELED:
                CancelPaymentCommand cancelPayment = (CancelPaymentCommand) command;
                return PaymentCanceledEvent.builder()
                    .orderId(cancelPayment.getOrderId())
                    .userId(cancelPayment.getUserId())
                    .amount(cancelPayment.getAmount())
                    .paymentState(cancelPayment.getPaymentState())
                    .paymentId(cancelPayment.getPaymentId())
                    .build();
            case ORDER_REJECTED:
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
            case CREATE_ORDER:
                OrderCreatedEvent createCmd = (OrderCreatedEvent) event;
                return CreateOrderCommand.builder()
                    .orderId(createCmd.getOrderId())
                    .productId(createCmd.getProductId())
                    .quantity(createCmd.getQuantity())
                    .state(createCmd.getState())
                    .build();
            case PROCESS_PAYMENT:
                PaymentProcessedEvent paymentCmd = (PaymentProcessedEvent) event;
                return ProcessPaymentCommand.builder()
                    .orderId(paymentCmd.getOrderId())
                    .paymentId(paymentCmd.getPaymentId())
                    .paymentState(paymentCmd.getPaymentState())
                    .userId(paymentCmd.getUserId())
                    .build();
            case RESERVE_PRODUCT:
                ProductReservedEvent reserveCmd = (ProductReservedEvent) event;
                return ReserveProductCommand.builder()
                    .orderId(reserveCmd.getOrderId())
                    .userId(reserveCmd.getUserId())
                    .productId(reserveCmd.getProductId())
                    .quantity(reserveCmd.getQuantity())
                    .build();
            case APPROVE_ORDER:
                OrderApprovedEvent approvetCmd = (OrderApprovedEvent) event;
                return ApproveOrderCommand.builder()
                    .orderId(approvetCmd.getOrderId())
                    .state(approvetCmd.getState())
                    .userId(approvetCmd.getUserId())
                    .productQuantity(approvetCmd.getProductQuantity())
                    .productId(approvetCmd.getProductId())
                    .build();
            case PRODUCT_RESERVATION_CANCELED:
                ProductReservationCalseledEvent canceltCmd = (ProductReservationCalseledEvent) event;
                return ProductReservationCanselCommand.builder()
                    .orderId(canceltCmd.getOrderId())
                    .quantity(canceltCmd.getQuantity())
                    .userId(canceltCmd.getUserId())
                    .productId(canceltCmd.getProductId())
                    .reason(canceltCmd.getReason())
                    .build();
            case PAYMENT_CANCELED:
                PaymentCanceledEvent cancelPayment = (PaymentCanceledEvent) event;
                return CancelPaymentCommand.builder()
                    .orderId(cancelPayment.getOrderId())
                    .userId(cancelPayment.getUserId())
                    .amount(cancelPayment.getAmount())
                    .paymentState(cancelPayment.getPaymentState())
                    .paymentId(cancelPayment.getPaymentId())
                    .build();
            case ORDER_REJECTED:
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


    //read event from topic, create and send next command
    @Override
    public Command produceCommand(String currentTopic, String nextTopicCommand, Event event) {

        KStream<String, JsonNode> eventJson = this.builder.stream(currentTopic,
                                                                  Consumed.with(Serdes.String(), this.jsonSerde));
        //here we read stream from orderCreate method and topic and we consume what is produced from producer

            eventJson.foreach((key, value) -> {
                try {
                    this.event = this.mapper.readValue(value.traverse(), event.getClass());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Command createdCommand = commandFactory(this.event, nextTopicCommand);

            //KStream<String, Event> eventJson1 = this.builder.(CREATE_ORDER,
            //Produced.with(Serdes.String(), this.jsonSerde));
            // create the initial json object for balances
            //ObjectNode initialBalance = JsonNodeFactory.instance.objectNode(); for example
            //initialBalance.put("time", Instant.ofEpochMilli(0L).toString()); for example

            //here we produce and send new balance records to bank-balance-exactly-once topic in kafka as json
            //bankBalance.toStream().to("bank-balance-exactly-once", Produced.with(Serdes.String(), this.jsonSerde));

            //KafkaStreams streams = new KafkaStreams(this.builder.build(), getConsumerProperties());
            //streams.cleanUp();
            //streams.start();

            // shutdown hook to correctly close the streams application
            //Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

            //Event createdEvent = this.helpers.eventFactory(command,topic);

            // we write the data to the json document
            commandJson.put(createdCommand.getClass().getSimpleName(), createdCommand.toString());

            producer.send(
                new ProducerRecord<>(nextTopicCommand, command.getUserId(), commandJson.toString()));

            producer.close();

            return createdCommand;
    }


    //read command from topic, create and send event
    @Override
    public Event produceEvent(String currentTopic, String nextTopicCommand, Command command) {
        KStream<String, JsonNode> eventJson = this.builder.stream(currentTopic,
                                                                  Consumed.with(Serdes.String(), this.jsonSerde));
        //here we read stream from BankTransactionProducer we consume what is produced from producer
        //in order to get changes in balance and re balance the amount

        eventJson.foreach((key, value)->{
            try {
                this.command = this.mapper.readValue(value.traverse(), command.getClass());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Event createdEvent = eventFactory(this.command,nextTopicCommand);

        //KStream<String, Event> eventJson1 = this.builder.(CREATE_ORDER,
        //Produced.with(Serdes.String(), this.jsonSerde));
        // create the initial json object for balances
        //ObjectNode initialBalance = JsonNodeFactory.instance.objectNode(); for example
        //initialBalance.put("time", Instant.ofEpochMilli(0L).toString()); for example

        //here we produce and send new balance records to bank-balance-exactly-once topic in kafka as json
        //bankBalance.toStream().to("bank-balance-exactly-once", Produced.with(Serdes.String(), this.jsonSerde));

        //KafkaStreams streams = new KafkaStreams(this.builder.build(), getConsumerProperties());
        //streams.cleanUp();
        //streams.start();

        // shutdown hook to correctly close the streams application
        //Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        //Event createdEvent = this.helpers.eventFactory(command,topic);

        // we write the data to the json document
        commandJson.put(createdEvent.getClass().getSimpleName(), createdEvent.toString());

        producer.send(new ProducerRecord<>(nextTopicCommand, command.getUserId(), commandJson.toString()));

        producer.close();

        return createdEvent;
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
