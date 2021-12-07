package com.angel.models.events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.angel.models.states.OrderState;

@Getter
@Setter
public class OrderApprovedEvent extends Event{

    private OrderState state;
    private String productId;
    private int productQuantity;

    @Builder
    public OrderApprovedEvent(OrderState state, String orderId, String userId, String productId, int productQuantity){
        super(userId, orderId);
        this.state = state;
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

}
