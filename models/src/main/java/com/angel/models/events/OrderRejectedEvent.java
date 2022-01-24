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
    private OrderState state = OrderState.CANCELLED;
    private String paymentId;

    public OrderRejectedEvent(String a){}

    @Builder
    public OrderRejectedEvent(String reason, String userId, String orderId,String productId, String paymentId){
        super(userId, orderId, productId, 0);
        this.reason = reason;
        this.paymentId = paymentId;
    }

}
