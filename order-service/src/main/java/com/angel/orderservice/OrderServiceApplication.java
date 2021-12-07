package com.angel.orderservice;

import com.angel.saga.api.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.angel.orderservice"})
public class OrderServiceApplication {

    @Autowired
    private static Saga saga;

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);

        saga.handleOrderCreatedEvent();//handle orderchreated event and sends reserve product command 2
        saga.publishReserveProductCommand();// handle reserve product event and sends process payment command 3
        saga.publishProcessPaymentCommand();//5
        saga.publishApproveOrderCommand();//sends approve order command 7
        saga.handleOrderApprovedEvent();//handle appdoved order event 8
        saga.handleProductReservationCanceledEvent();//10
        saga.handleOrderRejectedEvent();//12
    }

}
