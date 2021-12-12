package com.angel.models.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReserveProductCommand extends Command{

    private String productId;
    private int quantity;
    private double price;

    @Builder
    public ReserveProductCommand(String orderId, String userId, String productId, int quantity, double price){
        super(userId,orderId);
        this.quantity = quantity;
        this.productId = productId;
        this.price = price;
    }

}
