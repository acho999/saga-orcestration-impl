package com.angel.kafkaconsumer.consumer;

import com.angel.models.api.IEvent;
import com.angel.models.commands.*;
import com.angel.models.events.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.connect.json.JsonDeserializer;
import org.apache.kafka.connect.json.JsonSerializer;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
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
    KafkaConsumer<String,String> consumer;
    private ObjectMapper objectMpper;

    public KafkaConsumerConfigImpl(){
        this.jsonSerializer = new JsonSerializer();
        this.jsonDeserializer = new JsonDeserializer();
        this.jsonSerde = Serdes.serdeFrom(jsonSerializer, jsonDeserializer);
        this.builder = new StreamsBuilder();
        this.mapper = new ObjectMapper();
        this.logger = Logger.getLogger("KafkaConsumerConfigImpl");
        this.consumer = new KafkaConsumer<>(this.getConsumerProperties());
        this.objectMpper = new ObjectMapper();
        this.init();
    }

    private void init(){
        consumer.subscribe(Arrays.asList(CREATE_ORDER_COMMAND,
                                         ORDER_CREATED_EVENT,
                                         RESERVE_PRODUCT_COMMAND,
                                         PRODUCT_RESERVED_EVENT,
                                         PROCESS_PAYMENT_COMMAND,
                                         PAYMENT_PROCESSED_EVENT,
                                         APPROVE_ORDER_COMMAND,
                                         ORDER_APPROVED_EVENT,
                                         REJECT_ORDER_COMMAND,
                                         ORDER_REJECTED_EVENT,
                                         CANCEL_PRODUCT_RESERVATION_COMMAND,
                                         PRODUCT_RESERVATION_CANCELED_EVENT,
                                         CANCEL_PAYMENT_COMMAND,
                                         PAYMENT_CANCELED_EVENT));
    }

    public Event eventFactory(Command cmd, String topic){
        if (cmd == null){
            return null;
        }
        switch (topic){//1
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
                    .price(paymentCmd.getPrice())
                    .quantity(paymentCmd.getQuantity())
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
                    .build();
            case PRODUCT_RESERVATION_CANCELED_EVENT:
                ProductReservationCanselCommand canceltCmd = (ProductReservationCanselCommand) cmd;
                return ProductReservationCalseledEvent.builder()
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
                    .amount(cancelPayment.getAmount())
                    .paymentState(cancelPayment.getPaymentState())
                    .paymentId(cancelPayment.getPaymentId())
                    .build();
            case ORDER_REJECTED_EVENT:
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
//            case CREATE_ORDER_COMMAND:
//                OrderCreatedEvent createCmd = (OrderCreatedEvent) evt;
//                return CreateOrderCommand.builder()
//                    .orderId(createCmd.getOrderId())
//                    .productId(createCmd.getProductId())
//                    .quantity(createCmd.getQuantity())
//                    .state(createCmd.getState())
//                    .build();
            case PROCESS_PAYMENT_COMMAND:
                ProductReservedEvent paymentCmd = (ProductReservedEvent) evt;
                return ProcessPaymentCommand.builder()
                    .orderId(paymentCmd.getOrderId())
                    .userId(paymentCmd.getUserId())
                    .price(paymentCmd.getPrice())
                    .quantity(paymentCmd.getQuantity())
                    .paymentId(null)
                    .paymentState(null)
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
                    .build();
            case CANCEL_PRODUCT_RESERVATION_COMMAND:
                ProductReservationCalseledEvent canceltCmd = (ProductReservationCalseledEvent) evt;
                return ProductReservationCanselCommand.builder()
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
                    .amount(cancelPayment.getAmount())
                    .paymentState(cancelPayment.getPaymentState())
                    .paymentId(cancelPayment.getPaymentId())
                    .build();
            case REJECT_ORDER_COMMAND:
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
    public Command readEvent(String currentTopic, String nextTopicCommand, Event evt, ConsumerRecord<String, String> record)
        throws JsonProcessingException {

        JsonNode actualObj = this.objectMpper.readTree(record.value());
        this.event = this.mapper.readValue(convertObjToJson(actualObj, evt), evt.getClass());
        Command createdCommand = commandFactory(this.event, nextTopicCommand);
        return createdCommand;
    }

    private String convertObjToJson(JsonNode jsonObj, IEvent evt){
        String classname = evt.getClass().getSimpleName();
        JsonNode obj = jsonObj.get(classname);
        String actual = obj.toString().replaceAll("\\\\","").replaceAll("^\"|\"$", "");
        return actual;
    }

    //read command from topic, create and send event
    @Override
    public Event readCommand(String currentTopic, String nextTopicCommand, Command cmd, ConsumerRecord<String, String> record)
        throws JsonProcessingException {

        if(record == null){
            this.command = cmd;
        } else {
            JsonNode actualObj = this.objectMpper.readTree(record.value());
            this.command = this.mapper.readValue(convertObjToJson(actualObj, cmd), cmd.getClass());
        }
        //System.out.println(record.value());

        Event createdEvent = eventFactory(this.command,nextTopicCommand);
        return createdEvent;
    }

    public void consumeTest(){
        KafkaConsumer<String,String> consumer = new KafkaConsumer<>(this.getConsumerProperties());

        consumer.subscribe(Arrays.asList("testTopic"));

        //StreamsBuilder builder = new StreamsBuilder();

//        KStream<String, JsonNode> eventJson = this.builder.stream("testTopic",
//                                                  Consumed.with(Serdes.String(), this.jsonSerde));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            if (records.isEmpty()){continue;}
            for (ConsumerRecord<String, String> record : records){
                //this.logger.info();
                //System.out.println(record.key().toString());
                System.out.println(record.value().toString());
            }
        }

        // shutdown hook to correctly close the streams application
        //Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

    }

    public Properties getConsumerProperties(){
        Properties config = new Properties();

        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "E-commerce-application");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "newApp3");
        config.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0");//10 * 1024 * 1024L);
        config.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);

        return config;
    }

}
