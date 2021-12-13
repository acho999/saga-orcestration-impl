package com.angel.models.commands;

import com.angel.models.states.OrderState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RejectOrderCommand extends Command{

    private String reason;
    private OrderState state = OrderState.ORDER_CANCELLED;
    private String productId;

    public RejectOrderCommand(String a){}


    @Builder
    public RejectOrderCommand(String reason, String userId, String orderId,String productId){
        super(userId, orderId);
        this.reason = reason;
        this.productId = productId;
    }

}
