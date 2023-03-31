package com.angel.models.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.angel.models.states.OrderState;

@Getter
@NoArgsConstructor
public final class ApproveOrderCommand extends Command{
//POJO class should be immutable (without setter and final also is thread safe)

    private OrderState state;

    public ApproveOrderCommand(String a){}


    @Builder
    public ApproveOrderCommand(String orderId, String userId, String productId, int quantity){
        super(userId, orderId, productId, quantity);
        this.state = OrderState.COMPLETED;
    }

}
