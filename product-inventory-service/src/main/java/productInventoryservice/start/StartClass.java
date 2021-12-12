package productInventoryservice.start;

import com.angel.models.commands.RejectOrderCommand;
import com.angel.models.commands.ReserveProductCommand;
import com.angel.saga.api.SagaOrchestration;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import productInventoryservice.services.api.ProductInventoryService;

import java.time.Duration;
import java.util.Arrays;

import static com.angel.models.constants.TopicConstants.*;

@Component
public class StartClass {



    private SagaOrchestration sagaOrchestration;

    private ProductInventoryService service;

    private KafkaConsumer<String,String> consumer;

    @Autowired
    public StartClass(ProductInventoryService service, SagaOrchestration sagaOrchestration){
        this.sagaOrchestration = sagaOrchestration;
        this.service = service;
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


    public void runAll() throws JsonProcessingException {

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            for(ConsumerRecord<String, String> record : records){

                switch(record.topic()){

                    case PRODUCT_RESERVED_EVENT :
                        ReserveProductCommand event =
                            (ReserveProductCommand) sagaOrchestration.handleProductReservedEvent(record);//4

                        if (event != null && !service.isAvailable(event.getProductId(), event.getQuantity())) {
                            sagaOrchestration.publishCancelProductReservationCommand(record);//9
                            sagaOrchestration.publishCancelPaymentCommand(record);//11
                            break;
                        }

                        if (event != null) {
                            service.isAvailable(event.getProductId(), event.getQuantity());
                        }
                        break;

                    case PRODUCT_RESERVATION_CANCELED_EVENT :
                        RejectOrderCommand command =
                        (RejectOrderCommand) sagaOrchestration.handleProductReservationCanceledEvent(record);//10
                        if (command != null) {
                            service.resetQuantity(command.getProductId());
                            break;
                        }

                    default: break;
                }

            }
        }
    }

}
