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
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private ObjectMapper objectMapper;

    public KafkaProducerConfigImpl(){
        this.builder = new StreamsBuilder();
        this.mapper = new ObjectMapper();
        this.producer = new KafkaProducer<>(getProducerProperties());
        this.commandJson = JsonNodeFactory.instance.objectNode();
        this.logger = Logger.getLogger("KafkaProducerConfigImpl");
        this.objectMapper = new ObjectMapper();
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

    public void sendCommand(String nextTopicCommand, Command createdCommand)
        throws JsonProcessingException {
        if (nextTopicCommand == null || createdCommand == null){
            return;
        }
        String commandName = createdCommand.getClass().getSimpleName();

        String crCommand = this.objectMapper.writeValueAsString(createdCommand);

        commandJson.put(commandName, crCommand);

        String json = commandJson.toString();

        String createdC = createdCommand.getUserId();

        System.out.println(commandJson.toString());

        producer.send(
            new ProducerRecord<>(nextTopicCommand, createdC, json));

        //producer.close();
    }

    public void sendEvent(String nextTopicCommand, Event createdEvent)
        throws JsonProcessingException {
        if (nextTopicCommand == null || createdEvent == null){
            return;
        }
        String className = createdEvent.getClass().getSimpleName();

        String createdE = createdEvent.getUserId();

        String crEvent = this.objectMapper.writeValueAsString(createdEvent);

        commandJson.put(className, crEvent);

        System.out.println(commandJson);

        String json = commandJson.toString();

        producer.send(
            new ProducerRecord<>(nextTopicCommand, createdE, json));

        //producer.close();
    }

    public Properties getConsumerProperties(){
        Properties config = new Properties();

        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "E-commerce-application");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9091");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
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

        System.out.println(commandJson.toString());

        producer.send(
            new ProducerRecord<>("createOrderEvent", "KEY", commandJson.toString()));
        //producer.close();
    }
}
