package com.angel.kafkautils;

import com.angel.kafkautils.consumer.KafkaConsumerConfigImpl;
import com.angel.kafkautils.producer.KafkaProducerConfigImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaUtilsApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaUtilsApplication.class, args);

        KafkaConsumerConfigImpl a = new KafkaConsumerConfigImpl();
        a.orderCreatedEvent();

        KafkaProducerConfigImpl b = new KafkaProducerConfigImpl();


    }

}
