package com.angel.models.events;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.angel.models.states.OrderState;

@Getter
@Setter
@NoArgsConstructor
public class OrderRejectedEvent extends Event{

    private String reason;
    private OrderState state = OrderState.ORDER_CANCELLED;
    private String productId;

    @Builder
    public OrderRejectedEvent(String reason, String userId, String orderId,String productId){
        super(userId, orderId);
        this.reason = reason;
        this.productId = productId;
    }

}
