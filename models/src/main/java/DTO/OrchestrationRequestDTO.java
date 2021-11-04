package DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrchestrationRequestDTO {

    private int userId;
    private int productId;
    private String orderId;
    private double amount;

    public static class Builder {

        private int userId;
        private int productId;
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

        public Builder setProductId(Integer productId) {
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
