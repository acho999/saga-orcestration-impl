package com.angel.models.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.angel.models.states.OrderState;

@Getter
@NoArgsConstructor
public class CreateOrderCommand extends Command{
//POJO class should be immutable (without setter and final also is thread safe)
    private OrderState state;

    public CreateOrderCommand(String a){}


    @Builder
    public CreateOrderCommand(String orderId, String userId, String productId, int quantity,
                              OrderState state){
        super(userId, orderId, productId, quantity);
        this.state = state;
    }

}
