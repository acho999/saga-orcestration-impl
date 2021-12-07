package com.angel.models.commands;

import com.angel.models.states.OrderState;
import com.angel.models.states.PaymentState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelPaymentCommand extends Command{

    private String paymentId;
    private PaymentState paymentState;
    private double amount;

    @Builder
    public CancelPaymentCommand(String paymentId, PaymentState paymentState, String orderId, String userId,double amount){
        super(userId, orderId);
        this.paymentState = paymentState;
        this.paymentId = paymentId;
        this.amount = amount;
    }

}
