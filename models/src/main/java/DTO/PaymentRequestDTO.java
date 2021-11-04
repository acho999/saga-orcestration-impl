package DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDTO {

    private Integer userId;
    private String orderId;
    private Double amount;

    public static class Builder {

        private Integer userId;
        private String orderId;
        private Double amount;

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
