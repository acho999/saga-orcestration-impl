package com.angel.models.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.angel.models.states.OrderState;

@Getter
@Setter
@NoArgsConstructor
public class ApproveOrderCommand extends Command{

    private OrderState state;

    public ApproveOrderCommand(String a){}


    @Builder
    public ApproveOrderCommand(String orderId, String userId, String productId, int quantity, double price){
        super(userId, orderId, price, productId, quantity);
        this.state = OrderState.COMPLETED;
    }

}
