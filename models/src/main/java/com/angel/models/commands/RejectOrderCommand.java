package com.angel.models.commands;

import com.angel.models.states.OrderState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RejectOrderCommand extends Command{

    private String reason;
    private OrderState state = OrderState.ORDER_CANCELLED;
    private String productId;

    @Builder
    public RejectOrderCommand(String reason, String userId, String orderId,String productId){
        super(userId, orderId);
        this.reason = reason;
        this.productId = productId;
    }

}
