package paymentsservice;

import com.angel.models.commands.Command;
import com.angel.models.commands.ProcessPaymentCommand;
import com.angel.models.events.Event;
import com.angel.models.events.PaymentProcessedEvent;
import com.angel.models.events.ProductReservedEvent;
import com.angel.models.states.PaymentState;
import com.angel.saga.api.Saga;
import com.angel.saga.impl.SagaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import paymentsservice.models.Payment;
import paymentsservice.services.impl.PaymentsServiceImpl;

@SpringBootApplication(scanBasePackages = {"paymentsservice"})
public class PaymentsServiceApplication {

    @Autowired
    private static Saga saga;

    @Autowired
    private static  PaymentsServiceImpl paymentsService;

    public static void main(String[] args) {
        SpringApplication.run(PaymentsServiceApplication.class, args);
        runAll();
    }

    private static void runAll(){

        ProcessPaymentCommand command = (ProcessPaymentCommand)saga.handlePaymentProcessedEvent();//6

        if (!paymentsService.savePayment(command.getUserId(),new Payment(PaymentState.PAYMENT_APPROVED,
                                                                         command.getAmount()))){
            saga.publishRejectOrderCommand();//11
            saga.publishCancelProductReservationCommand();//9
            return;
        }
        paymentsService.savePayment(command.getUserId(),new Payment(PaymentState.PAYMENT_APPROVED,
                                                                    command.getAmount()));

    }

}
