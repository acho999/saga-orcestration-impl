package DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrchestrationRequestDTO {

    private String userId;
    private String productId;
    private String orderId;
    private double amount;

    public static class Builder {

        private String userId;
        private String productId;
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

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setProductId(String productId) {
            this.productId = productId;
            return this;
        }

        public OrchestrationRequestDTO build() {
            return new OrchestrationRequestDTO(this);
        }


    }

    private OrchestrationRequestDTO(Builder builder){
        this.orderId = builder.orderId;
        this.userId = builder.userId;
        this.amount = builder.amount;
        this.productId = builder.productId;
    }

}
