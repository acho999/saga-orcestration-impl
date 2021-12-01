package commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import states.PaymentState;

@Getter
@Setter
public class ProcessPaymentCommand extends Command{

        private String paymentId;
        private PaymentState payment;

        @Builder
        public ProcessPaymentCommand(String paymentId, PaymentState payment, String orderId, String userId){
                super(orderId, userId);
                this.payment = payment;
                this.paymentId = paymentId;
        }


}
