package paymentsservice.start;

import com.angel.models.commands.CancelPaymentCommand;
import com.angel.models.commands.ProcessPaymentCommand;
import com.angel.models.states.PaymentState;
import com.angel.saga.api.SagaOrchestration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import paymentsservice.models.Payment;
import paymentsservice.services.api.PaymentsService;
import paymentsservice.services.impl.PaymentsServiceImpl;

@Component
public class StartClass {

    @Autowired
    private SagaOrchestration sagaOrchestration;

    @Autowired
    private PaymentsService paymentsService;

    public void runAll() {

        CancelPaymentCommand cmd =
            (CancelPaymentCommand) this.sagaOrchestration.handlePaymentCanceledEvent();//12

        ProcessPaymentCommand command =
            (ProcessPaymentCommand) this.sagaOrchestration.handlePaymentProcessedEvent();//6


        if (command != null && !this.paymentsService.savePayment(command.getUserId(),
                                         new Payment(PaymentState.PAYMENT_APPROVED,
                                                     (command.getPrice() * command.getQuantity())))) {
            this.sagaOrchestration.publishCancelPaymentCommand();//11
            this.sagaOrchestration.publishCancelProductReservationCommand();//9
        }

        if (cmd != null) {
            this.paymentsService.reversePayment(cmd.getUserId(), cmd.getPaymentId());
            return;
        }

        if (command != null) {
            this.paymentsService.savePayment(command.getUserId(),
                                        new Payment(PaymentState.PAYMENT_APPROVED,
                                                    (command.getPrice() * command.getQuantity())));
        }


    }

}
