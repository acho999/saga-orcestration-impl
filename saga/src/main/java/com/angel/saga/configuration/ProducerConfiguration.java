package com.angel.saga.configuration;

import com.angel.models.api.IEvent;
import com.angel.saga.impl.IEventSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import static com.angel.models.constants.TopicConstants.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class ProducerConfiguration {

    @Bean
    public ProducerFactory<String, IEvent> producerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IEventSerializer.class);
        properties.put(ProducerConfig.ACKS_CONFIG, "all"); // strongest producing guarantee
        properties.put(ProducerConfig.RETRIES_CONFIG, "3");
        properties.put(ProducerConfig.LINGER_MS_CONFIG, "1");
        // leverage idempotent producer from Kafka 0.11 !
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true"); // ensure we don't push duplicates

        return new DefaultKafkaProducerFactory<String, IEvent>(properties);
    }

    @Bean
    public KafkaTemplate<String, IEvent> kafkaTemplate() {
        return new KafkaTemplate(producerFactory());
    }

}
