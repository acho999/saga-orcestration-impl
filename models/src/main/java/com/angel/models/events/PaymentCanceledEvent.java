package com.angel.models.events;

import com.angel.models.states.PaymentState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public final class PaymentCanceledEvent extends Event{
//POJO class should be immutable (without setter and final also is thread safe)
    private String paymentId;
    private PaymentState paymentState;

    public PaymentCanceledEvent(String a){}

    @Builder
    public PaymentCanceledEvent(String paymentId, PaymentState paymentState,
                                String orderId, String userId,
                                String productId, int quantity){
        super(userId, orderId, productId, quantity);
        this.paymentState = paymentState;
        this.paymentId = paymentId;
    }

}
