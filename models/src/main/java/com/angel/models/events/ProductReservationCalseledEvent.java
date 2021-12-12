package com.angel.models.events;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductReservationCalseledEvent extends Event{

    private String productId;
    private int quantity;
    private String reason;

    @Builder
    public ProductReservationCalseledEvent(String productId, int quantity, String reason, String userId, String orderId){
        super(userId, orderId);
        this.productId = productId;
        this.quantity = quantity;
        this.reason = reason;
    }

}
