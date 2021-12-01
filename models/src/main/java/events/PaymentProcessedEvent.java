package events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import states.PaymentState;

@Getter
@Setter
public class PaymentProcessedEvent extends Event{

    private String paymentId;
    private PaymentState payment;

    @Builder
    public PaymentProcessedEvent(String paymentId, PaymentState payment, String orderId, String userId){
        super(userId, orderId);
        this.payment = payment;
        this.paymentId = paymentId;
    }
}
