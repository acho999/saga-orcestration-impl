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
    private double amount;

    @Builder
    public PaymentProcessedEvent(String paymentId, PaymentState paymentState, String orderId, String userId,double amount){
        super(userId, orderId);
        this.paymentState = paymentState;
        this.paymentId = paymentId;
        this.amount = amount;
    }
}
