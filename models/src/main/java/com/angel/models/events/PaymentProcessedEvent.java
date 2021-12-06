package com.angel.models.events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.angel.models.states.PaymentState;

@Getter
@Setter
public class PaymentProcessedEvent extends Event{

    private String paymentId;
    private PaymentState paymentState;

    @Builder
    public PaymentProcessedEvent(String paymentId, PaymentState paymentState, String orderId, String userId){
        super(userId, orderId);
        this.paymentState = paymentState;
        this.paymentId = paymentId;
    }
}
