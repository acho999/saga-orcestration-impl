package com.angel.models.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.angel.models.states.OrderState;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrderCommand extends Command{

    private String productId;
    private int quantity;
    private OrderState state;

    @Builder
    public CreateOrderCommand(String orderId, String userId, String productId, int quantity, OrderState state){
        super(userId,orderId);
        this.quantity = quantity;
        this.productId = productId;
        this.state = state;
    }

}
