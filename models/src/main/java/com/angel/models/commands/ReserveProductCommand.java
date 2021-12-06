package com.angel.models.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReserveProductCommand extends Command{

    private String productId;
    private int quantity;

    @Builder
    public ReserveProductCommand(String orderId, String userId, String productId, int quantity){
        super(userId,orderId);
        this.quantity = quantity;
        this.productId = productId;
    }

}
