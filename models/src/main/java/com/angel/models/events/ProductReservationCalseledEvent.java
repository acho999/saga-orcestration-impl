package com.angel.models.events;

import com.angel.models.states.PaymentState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductReservationCalseledEvent extends Event{

    private String reason;
    private String paymentId;
    private PaymentState paymentState;

    public ProductReservationCalseledEvent(String a){}


    @Builder
    public ProductReservationCalseledEvent(String productId, int quantity, String reason, String userId, String orderId, double price){
        super(userId, orderId, price, productId, quantity);
        this.reason = reason;
    }

}
