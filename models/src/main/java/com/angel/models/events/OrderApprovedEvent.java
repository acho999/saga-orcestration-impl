package com.angel.models.events;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.angel.models.states.OrderState;

@Getter
@NoArgsConstructor
public final class OrderApprovedEvent extends Event{
//POJO class should be immutable (without setter and final also is thread safe)
    private OrderState state;

    public OrderApprovedEvent(String a){}

    @Builder
    public OrderApprovedEvent(OrderState state, String orderId, String userId, String productId, int quantity){
        super(userId, orderId, productId, quantity);
        this.state = state;
    }

}
