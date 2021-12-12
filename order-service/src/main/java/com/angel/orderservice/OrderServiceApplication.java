package com.angel.orderservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.angel.orderservice"})
public class OrderServiceApplication {


    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(OrderServiceApplication.class, args);
//        ApplicationContext context = ApplicationContextUtils.getApplicationContext();
//        StartClass start = context.getBean(StartClass.class);
//        start.runAll();
    }
}
