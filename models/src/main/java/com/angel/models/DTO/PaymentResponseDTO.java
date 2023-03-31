package com.angel.models.DTO;

import lombok.Getter;
import lombok.Setter;
import com.angel.models.states.PaymentState;

@Getter
public class PaymentResponseDTO {
    //POJO class should be immutable (without setter and final also is thread safe)

    private String id;
    private String userId;
    private String orderId;
    private int quantity;
    private double price;
    private PaymentState state;
    private String productId;
    private double amount = this.price * this.quantity;

//    public static class Builder {
//
//        private String userId;
//        private String orderId;
//        private double amount;
//        private PaymentState state;
//
//        public Builder setOrderId(String orderId) {
//            this.orderId = orderId;
//            return this;
//        }
//
//        public Builder setAmount(Double amount) {
//            this.amount = amount;
//            return this;
//        }
//
//        public Builder setUserId(String userId) {
//            this.userId = userId;
//            return this;
//        }
//
//        public Builder setState(PaymentState state) {
//            this.state = state;
//            return this;
//        }
//
//        public PaymentResponseDTO build() {
//            return new PaymentResponseDTO(this);
//        }
//
//
//    }
//
//    private PaymentResponseDTO(Builder builder){
//        this.orderId = builder.orderId;
//        this.userId = builder.userId;
//        this.amount = builder.amount;
//        this.state = builder.state;
//    }

}
