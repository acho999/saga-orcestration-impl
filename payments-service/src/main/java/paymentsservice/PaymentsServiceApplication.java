package paymentsservice;

import com.angel.models.commands.CancelPaymentCommand;
import com.angel.models.commands.ProcessPaymentCommand;
import com.angel.models.states.PaymentState;
import com.angel.saga.api.SagaOrchestration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import paymentsservice.models.Payment;
import paymentsservice.services.impl.PaymentsServiceImpl;

@SpringBootApplication(scanBasePackages = {"paymentsservice"})
public class PaymentsServiceApplication {

    @Autowired
    private static SagaOrchestration sagaOrchestration;

    @Autowired
    private static PaymentsServiceImpl paymentsService;

    public static void main(String[] args) {
        SpringApplication.run(PaymentsServiceApplication.class, args);
        runAll();
    }

    private static void runAll() {

        ProcessPaymentCommand command =
            (ProcessPaymentCommand) sagaOrchestration.handlePaymentProcessedEvent();//6

        if (!paymentsService.savePayment(command.getUserId(),
                                         new Payment(PaymentState.PAYMENT_APPROVED,
                                                     command.getAmount()))) {
            sagaOrchestration.publishCancelPaymentCommand();//11
            sagaOrchestration.publishCancelProductReservationCommand();//9
        }

        CancelPaymentCommand cmd =
            (CancelPaymentCommand) sagaOrchestration.handlePaymentCanceledEvent();//12

        if (cmd != null) {
            paymentsService.reversePayment(cmd.getUserId(), cmd.getPaymentId());
            return;
        }

        if (command != null) {
            paymentsService.savePayment(command.getUserId(),
                                        new Payment(PaymentState.PAYMENT_APPROVED,
                                                    command.getAmount()));
        }


    }

}
