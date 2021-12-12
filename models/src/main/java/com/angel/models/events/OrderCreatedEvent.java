package com.angel.models.events;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.angel.models.states.OrderState;

@Getter
@Setter
@NoArgsConstructor
public class OrderCreatedEvent extends Event {

    private String productId;
    private int quantity;
    private OrderState state;


    @Builder
    public OrderCreatedEvent(String orderId, String userId, String productId, int quantity, OrderState state){
        super(userId,orderId);
        this.quantity = quantity;
        this.productId = productId;
        this.state = state;
    }

}
