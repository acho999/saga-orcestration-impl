package com.angel.models.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductReservationCanselCommand extends Command{

    private String productId;
    private int quantity;
    private String reason;

    public ProductReservationCanselCommand(String a){}


    @Builder
    public ProductReservationCanselCommand(String productId, int quantity, String reason, String userId, String orderId){
        super(userId, orderId);
        this.productId = productId;
        this.quantity = quantity;
        this.reason = reason;
    }

}
