package com.angel.models.events;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.angel.models.states.PaymentState;

@Getter
@Setter
@NoArgsConstructor
public class PaymentProcessedEvent extends Event{

    private String paymentId;
    private PaymentState paymentState;

    public PaymentProcessedEvent(String a){}


    @Builder
    public PaymentProcessedEvent(String paymentId, PaymentState paymentState, String orderId, String userId,double price,int quantity, String productId){
        super(userId, orderId, price, productId, quantity);
        this.paymentState = paymentState;
        this.paymentId = paymentId;
    }
}
