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

    private OrderState state;

    public CreateOrderCommand(String a){}


    @Builder
    public CreateOrderCommand(String orderId, String userId, String productId, int quantity,
                              OrderState state){
        super(userId, orderId, productId, quantity);
        this.state = state;
    }

}
