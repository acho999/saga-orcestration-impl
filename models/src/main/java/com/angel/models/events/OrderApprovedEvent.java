package com.angel.models.events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.angel.models.states.OrderState;

@Getter
@Setter
public class OrderApprovedEvent extends Event{

    private OrderState state;

    @Builder
    public OrderApprovedEvent(OrderState state, String orderId, String userId){
        super(userId, orderId);
        this.state = state;
    }

}
