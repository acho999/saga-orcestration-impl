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
    private String productId;
    private int quantity;

    public ApproveOrderCommand(String a){}


    @Builder
    public ApproveOrderCommand(String orderId, String userId, String productId, int quantity){
        super(userId, orderId);
        this.state = OrderState.ORDER_COMPLETED;
        this.productId = productId;
        this.quantity = quantity;
    }

}
