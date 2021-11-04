package DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDTO {

    private int userId;
    private String orderId;
    private double amount;

    public static class Builder {

        private int userId;
        private String orderId;
        private double amount;

        public Builder setOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setAmount(Double amount) {
            this.amount = amount;
            return this;
        }

        public Builder setUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public PaymentRequestDTO build() {
            return new PaymentRequestDTO(this);
        }


    }

    private PaymentRequestDTO(Builder builder){
        this.orderId = builder.orderId;
        this.userId = builder.userId;
        this.amount = builder.amount;
    }

}
