package com.angel.models.events;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.angel.models.states.OrderState;
import org.springframework.core.annotation.Order;

@Getter
@Setter
@NoArgsConstructor
public class OrderCreatedEvent extends Event {

    private OrderState state;

    public OrderCreatedEvent(String a){}
    @Builder
    public OrderCreatedEvent(String orderId, String userId, String productId, int quantity, OrderState state){
        super(userId, orderId, productId, quantity);
        this.state = state;
    }

}
