package com.angel.models.commands;

import com.angel.models.states.OrderState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class RejectOrderCommandPayment extends Command{
//POJO class should be immutable (without setter and final also is thread safe)
    private String reason;
    private OrderState state = OrderState.CANCELLED;
    private String paymentId;

    public RejectOrderCommandPayment(String a){}


    @Builder
    public RejectOrderCommandPayment(String reason, String userId, String orderId, String productId, String paymentId){
        super(userId, orderId, productId, 0);
        this.reason = reason;
        this.paymentId = paymentId;
    }

}
