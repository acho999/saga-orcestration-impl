package com.angel.models.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.angel.models.states.PaymentState;

@Getter
@Setter
public class ProcessPaymentCommand extends Command{

        private String paymentId;
        private PaymentState paymentState;
        private double amount;

        @Builder
        public ProcessPaymentCommand(String paymentId, PaymentState paymentState, String orderId, String userId,double amount){
                super(orderId, userId);
                this.paymentState = paymentState;
                this.paymentId = paymentId;
                this.amount = amount;
        }


}
