package com.angel.saga.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;

import static com.angel.models.constants.TopicConstants.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaTopicConfig {
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic("approveOrderCommand", 1, (short) 1);
    }
    @Bean
    public NewTopic topic2() {
        return new NewTopic("approveOrderEvent", 1, (short) 1);
    }
    @Bean
    public NewTopic topic3() {
        return new NewTopic("createOrderCommand", 1, (short) 1);
    }
    @Bean
    public NewTopic topic4() {
        return new NewTopic("createOrderEvent", 1, (short) 1);
    }
    @Bean
    public NewTopic topic5() {
        return new NewTopic("orderRejectCommand", 1, (short) 1);
    }
    @Bean
    public NewTopic topic6() {
        return new NewTopic("orderRejectedEvent", 1, (short) 1);
    }
    @Bean
    public NewTopic topic7() {
        return new NewTopic("paymentCancelCommand", 1, (short) 1);
    }
    @Bean
    public NewTopic topic8() {
        return new NewTopic("paymentCanceledEvent", 1, (short) 1);
    }
    @Bean
    public NewTopic topic9() {
        return new NewTopic("processPaymentCommand", 1, (short) 1);
    }
    @Bean
    public NewTopic topic10() {
        return new NewTopic("processPaymentEvent", 1, (short) 1);
    }
    @Bean
    public NewTopic topic11() {
        return new NewTopic("productReservationCancelCommand", 1, (short) 1);
    }
    @Bean
    public NewTopic topic12() {
        return new NewTopic("productReservationCanceledEvent", 1, (short) 1);
    }
    @Bean
    public NewTopic topic13() {
        return new NewTopic("reserveProductCommand", 1, (short) 1);
    }
    @Bean
    public NewTopic topic14() {
        return new NewTopic("reserveProductEvent", 1, (short) 1);
    }
    @Bean
    public NewTopic topic15() {
        return new NewTopic("getProductPrice", 1, (short) 1);
    }
    @Bean
    public NewTopic topic16() {
        return new NewTopic("setProductPrice", 1, (short) 1);
    }

}
