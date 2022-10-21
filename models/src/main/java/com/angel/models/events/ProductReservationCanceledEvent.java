package com.angel.models.events;

import com.angel.models.states.PaymentState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductReservationCanceledEvent extends Event{

    private String reason;
    private String paymentId;
    private PaymentState paymentState;

    public ProductReservationCanceledEvent(String a){}


    @Builder
    public ProductReservationCanceledEvent(String productId, PaymentState paymentState, String paymentId, int quantity, String reason, String userId, String orderId){
        super(userId, orderId, productId, quantity);
        this.paymentId = paymentId;
        this.paymentState = paymentState;
        this.reason = reason;
    }

}
