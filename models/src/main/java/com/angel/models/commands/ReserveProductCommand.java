package com.angel.models.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ReserveProductCommand extends Command{
//POJO class should be immutable (without setter and final also is thread safe)
    public ReserveProductCommand(String a){}


    @Builder
    public ReserveProductCommand(String orderId, String userId, String productId, int quantity){
        super(userId,orderId, productId, quantity);
    }

}
