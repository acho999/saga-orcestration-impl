package com.angel.models.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.angel.models.states.PaymentState;

@Getter
@Setter
@NoArgsConstructor
public class ProcessPaymentCommand extends Command{

        private String paymentId;
        private PaymentState paymentState;

        public ProcessPaymentCommand(String a){}


        @Builder
        public ProcessPaymentCommand(String paymentId, PaymentState paymentState, String orderId, String userId, double price, int quantity, String productId){
                super(userId, orderId, price, productId, quantity);
                this.paymentState = paymentState;
                this.paymentId = paymentId;
        }


}
