package com.angel.orderservice;

import com.angel.orderservice.appContext.ApplicationContextUtils;
import com.angel.orderservice.startClass.StartClass;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.angel.orderservice"})
public class OrderServiceApplication {


    public static void main(String[] args) throws JsonProcessingException, InterruptedException {
        SpringApplication.run(OrderServiceApplication.class, args);
        ApplicationContext context = ApplicationContextUtils.getApplicationContext();
        StartClass start = context.getBean(StartClass.class);
        start.runAll(null);
    }
}
