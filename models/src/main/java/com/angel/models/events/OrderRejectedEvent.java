package com.angel.models.events;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.angel.models.states.OrderState;

@Getter
@NoArgsConstructor
public final class OrderRejectedEvent extends Event{
//POJO class should be immutable (without setter and final also is thread safe)
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
