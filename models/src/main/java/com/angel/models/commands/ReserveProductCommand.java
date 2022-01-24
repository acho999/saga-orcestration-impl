package com.angel.models.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReserveProductCommand extends Command{

    public ReserveProductCommand(String a){}


    @Builder
    public ReserveProductCommand(String orderId, String userId, String productId, int quantity){
        super(userId,orderId, productId, quantity);
    }

}
