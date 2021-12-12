package com.angel.orderservice.startClass;

import com.angel.models.commands.Command;
import com.angel.models.commands.CreateOrderCommand;
import com.angel.orderservice.services.api.OrdersService;
import com.angel.saga.api.SagaOrchestration;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;

import static com.angel.models.constants.TopicConstants.*;

@Component
public class StartClass {


    private SagaOrchestration sagaOrchestration;

    private OrdersService service;

    private KafkaConsumer<String,String> consumer;

    @Autowired
    public StartClass(OrdersService service, SagaOrchestration sagaOrchestration){
        this.service = service;
        this.sagaOrchestration = sagaOrchestration;
        this.consumer = new KafkaConsumer<>(this.sagaOrchestration.getConsumerProps());
        this.init();
    }

    private void init(){
        consumer.subscribe(Arrays.asList(CREATE_ORDER_COMMAND,
                                         ORDER_CREATED_EVENT,
                                         RESERVE_PRODUCT_COMMAND,
                                         PRODUCT_RESERVED_EVENT,
                                         PROCESS_PAYMENT_COMMAND,
                                         PAYMENT_PROCESSED_EVENT,
                                         APPROVE_ORDER_COMMAND,
                                         ORDER_APPROVED_EVENT,
                                         REJECT_ORDER_COMMAND,
                                         ORDER_REJECTED_EVENT,
                                         CANCEL_PRODUCT_RESERVATION_COMMAND,
                                         PRODUCT_RESERVATION_CANCELED_EVENT,
                                         CANCEL_PAYMENT_COMMAND,
                                         PAYMENT_CANCELED_EVENT));
    }

    public void runAll(Command command) throws JsonProcessingException {
        if(command.getClass().getSimpleName().equals("CreateOrderCommand")){
            //1
            this.sagaOrchestration.publishCreateOrderCommand(command,null);
        }
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for(ConsumerRecord<String, String> record : records){

                switch(record.topic()){

                    //case CREATE_ORDER_COMMAND:
                        //break;
                        
                    case ORDER_CREATED_EVENT:
                        //handle order created event and sends reserve product command 2
                        sagaOrchestration.handleOrderCreatedEvent(record); break;

                    case RESERVE_PRODUCT_COMMAND:
                        // handle reserve product command and sends process payment event 3
                        sagaOrchestration.publishReserveProductCommand(record);

                    case PROCESS_PAYMENT_COMMAND:
                        //5
                        sagaOrchestration.publishProcessPaymentCommand(record);break;

                    case APPROVE_ORDER_COMMAND:
                        //7 sends approve order command
                        sagaOrchestration.publishApproveOrderCommand(record); break;

                    case PRODUCT_RESERVATION_CANCELED_EVENT:
                        //10
                        sagaOrchestration.handleProductReservationCanceledEvent(record); break;

                    case PAYMENT_CANCELED_EVENT:
                        //12
                        sagaOrchestration.handlePaymentCanceledEvent(record); break;

                    case ORDER_APPROVED_EVENT :
                        Command approvedOrder =
                        sagaOrchestration.handleOrderApprovedEvent(record);//8 handle appdoved order event end of cycle without errors
                        if (approvedOrder != null) {
                            service.approveOrder(approvedOrder);
                            break;
                        }

                    case REJECT_ORDER_COMMAND:
                        sagaOrchestration.publishRejectOrderCommand(record);//14
//                        if (rejectedOrder != null) {
//                            service.cancelOrder(rejectedOrder);
//                            break;
//                        }

                    case ORDER_REJECTED_EVENT:
                        Command rejectedOrder = sagaOrchestration.handleOrderRejectedEvent(record);//14
                        if (rejectedOrder != null) {
                            service.cancelOrder(rejectedOrder);
                            break;
                        }

                    default: break;

                }

                this.sagaOrchestration.testConsumer(record);



            }
        }
    }
}
