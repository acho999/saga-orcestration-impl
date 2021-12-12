package com.angel.models.events;

import com.angel.models.states.PaymentState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentCanceledEvent extends Event{

    private String paymentId;
    private PaymentState paymentState;
    private double amount;

    @Builder
    public PaymentCanceledEvent(String paymentId, PaymentState paymentState, String orderId, String userId,double amount){
        super(userId, orderId);
        this.paymentState = paymentState;
        this.paymentId = paymentId;
        this.amount = amount;
    }

}
