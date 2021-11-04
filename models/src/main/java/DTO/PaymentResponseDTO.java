package DTO;

import lombok.Getter;
import lombok.Setter;
import states.PaymentState;

@Getter
@Setter
public class PaymentResponseDTO {

    private Integer userId;
    private String orderId;
    private Double amount;
    private PaymentState state;

    public static class Builder {

        private Integer userId;
        private String orderId;
        private Double amount;
        private PaymentState state;

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

        public Builder setState(PaymentState state) {
            this.state = state;
            return this;
        }

        public PaymentResponseDTO build() {
            return new PaymentResponseDTO(this);
        }


    }

    private PaymentResponseDTO(Builder builder){
        this.orderId = builder.orderId;
        this.userId = builder.userId;
        this.amount = builder.amount;
        this.state = builder.state;
    }

}
