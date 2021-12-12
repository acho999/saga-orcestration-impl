package paymentsservice.start;

import com.angel.models.commands.ProcessPaymentCommand;
import com.angel.models.events.PaymentCanceledEvent;
import com.angel.models.states.PaymentState;
import com.angel.saga.api.SagaOrchestration;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import paymentsservice.models.Payment;
import paymentsservice.services.api.PaymentsService;

import java.time.Duration;
import java.util.Arrays;

import static com.angel.models.constants.TopicConstants.*;

@Component
public class StartClass {

    private SagaOrchestration sagaOrchestration;

    private PaymentsService paymentsService;

    private KafkaConsumer<String,String> consumer;
    @Autowired
    public StartClass(PaymentsService paymentsService, SagaOrchestration sagaOrchestration){
        this.sagaOrchestration = sagaOrchestration;
        this.paymentsService = paymentsService;
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

                PaymentCanceledEvent cmd = null;

                ProcessPaymentCommand command = null;

                switch(record.topic()){

                    case CANCEL_PAYMENT_COMMAND :
                        //11
                        cmd = (PaymentCanceledEvent) this.sagaOrchestration.publishCancelPaymentCommand(record);
                        if (cmd != null) {
                            this.paymentsService.reversePayment(cmd.getUserId(), cmd.getPaymentId());
                            return;
                        }
                        break;


                    case PAYMENT_PROCESSED_EVENT :
                        //6
                        command = (ProcessPaymentCommand) this.sagaOrchestration.handlePaymentProcessedEvent(record);
                        if (command != null && !this.paymentsService.savePayment(command.getUserId(),
                                                                                 new Payment(
                                                                                     PaymentState.PAYMENT_APPROVED,
                                                                                     (command.getPrice()
                                                                                      * command.getQuantity())))) {
                            this.sagaOrchestration.publishCancelPaymentCommand(record);//11
                            this.sagaOrchestration.publishCancelProductReservationCommand(record);//9
                            break;
                        }
                        if (command != null) {
                            this.paymentsService.savePayment(command.getUserId(),
                                                             new Payment(PaymentState.PAYMENT_APPROVED,
                                                                         (command.getPrice()
                                                                          * command.getQuantity())));
                        }
                        break;

                }
            }
        }
    }

}
