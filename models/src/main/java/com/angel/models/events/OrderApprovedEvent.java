package com.angel.models.events;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.angel.models.states.OrderState;

@Getter
@Setter
@NoArgsConstructor
public class OrderApprovedEvent extends Event{

    private OrderState state;

    public OrderApprovedEvent(String a){}

    @Builder
    public OrderApprovedEvent(OrderState state, String orderId, String userId, String productId, int quantity, double price){
        super(userId, orderId, price, productId, quantity);
        this.state = state;
    }

}
