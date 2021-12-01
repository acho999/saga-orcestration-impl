package com.angel.kafkautils.producer;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Instant;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class KafkaProducerConfigImpl<T> implements IKafkaProducerConfig<T> {

    @Override
    public boolean processOrder(final T order){
        // creates an empty json {}
        ObjectNode transaction = JsonNodeFactory.instance.objectNode();

        // Instant.now() is to get the current time using Java 8
        Instant now = Instant.now();

        // we write the data to the json document
        transaction.put("time", now.toString());

        //return new ProducerRecord<>("bank-transactions", name, transaction.toString());

        return true;
    }

    private Producer<String, String> createProducer(){
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

        Producer<String, String> producer = new KafkaProducer<>(properties);

        return producer;
    }
}
