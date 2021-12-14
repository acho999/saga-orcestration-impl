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
    private String productId;
    private int quantity;

    public OrderApprovedEvent(String a){}

    @Builder
    public OrderApprovedEvent(OrderState state, String orderId, String userId, String productId, int quantity){
        super(userId, orderId);
        this.state = state;
        this.productId = productId;
        this.quantity = quantity;
    }

}
