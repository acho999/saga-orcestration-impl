package com.angel.orderservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.angel.orderservice", "com.angel.saga.configuration"})
public class OrderServiceApplication {


    public static void main(String[] args) throws JsonProcessingException, InterruptedException {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
