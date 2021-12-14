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
    private double price;
    private int quantity;
    private String productId;

    public PaymentProcessedEvent(String a){}


    @Builder
    public PaymentProcessedEvent(String paymentId, PaymentState paymentState, String orderId, String userId,double price,int quantity, String productId){
        super(userId, orderId);
        this.paymentState = paymentState;
        this.paymentId = paymentId;
        this.price = price;
        this.quantity = quantity;
        this.productId = productId;
    }
}
