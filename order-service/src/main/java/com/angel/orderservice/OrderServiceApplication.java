package com.angel.orderservice;

import com.angel.saga.api.SagaOrchestration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.angel.orderservice"})
public class OrderServiceApplication {

    @Autowired
    private static SagaOrchestration sagaOrchestration;

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);

        sagaOrchestration.handleOrderCreatedEvent();//handle order created event and sends reserve product command 2
        sagaOrchestration.publishReserveProductCommand();// handle reserve product command and sends process payment event 3
        sagaOrchestration.publishProcessPaymentCommand();//5
        sagaOrchestration.publishApproveOrderCommand();//sends approve order command 7
        sagaOrchestration.handleOrderApprovedEvent();//handle appdoved order event 8 end of cycle without errors
        sagaOrchestration.handleProductReservationCanceledEvent();//10
        sagaOrchestration.handleOrderRejectedEvent();//12
    }

}
