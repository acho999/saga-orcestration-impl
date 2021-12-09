package com.angel.orderservice;

import com.angel.models.commands.Command;
import com.angel.orderservice.services.api.OrdersService;
import com.angel.saga.api.SagaOrchestration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.angel.orderservice"})
public class OrderServiceApplication {

    @Autowired
    private static SagaOrchestration sagaOrchestration;

    @Autowired
    private static OrdersService service;

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);

        runAll();
    }

    private static void runAll() {
        sagaOrchestration.handleOrderCreatedEvent();//handle order created event and sends reserve product command 2
        sagaOrchestration.publishReserveProductCommand();// handle reserve product command and sends process payment event 3
        sagaOrchestration.publishProcessPaymentCommand();//5
        sagaOrchestration.publishApproveOrderCommand();//7 sends approve order command
        sagaOrchestration.handleProductReservationCanceledEvent();//10
        sagaOrchestration.handlePaymentCanceledEvent();//12

        Command approvedOrder = sagaOrchestration.handleOrderApprovedEvent();//8 handle appdoved order event end of cycle without errors
        if(approvedOrder!=null){
            service.approveOrder(approvedOrder);
            return;
        }
        Command rejectedOrder = sagaOrchestration.handleOrderRejectedEvent();//14
        if(rejectedOrder != null){
            service.cancelOrder(rejectedOrder);
            return;
        }
    }

}
