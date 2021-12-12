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
        private double price;
        private int quantity;

        @Builder
        public ProcessPaymentCommand(String paymentId, PaymentState paymentState, String orderId, String userId, double price, int quantity){
                super(userId, orderId);
                this.paymentState = paymentState;
                this.paymentId = paymentId;
                this.price = price;
                this.quantity = quantity;
        }


}
