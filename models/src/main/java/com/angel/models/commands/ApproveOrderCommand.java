package com.angel.models.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.angel.models.states.OrderState;

@Getter
@Setter
public class ApproveOrderCommand extends Command{

    private OrderState state;

    @Builder
    public ApproveOrderCommand(OrderState state, String orderId, String userId){
        super(userId, orderId);
        this.state = state;
    }

}
