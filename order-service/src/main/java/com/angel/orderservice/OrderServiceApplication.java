package com.angel.orderservice;

import com.angel.saga.api.Saga;
import com.angel.saga.impl.SagaImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.angel.orderservice"})
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
        Saga saga = new SagaImpl();
        saga.handleOrderCreatedEvent();//sends process payment command 2
        saga.publishApproveOrderCommand();//sends approve order command 7
        saga.handleOrderApprovedEvent();//handle appdoved order event 8

        //?
        saga.publishRejectOrderCommand();
    }

}
