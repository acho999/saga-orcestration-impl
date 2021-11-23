package DTO;

import lombok.Getter;
import lombok.Setter;
import states.OrderState;

@Getter
@Setter
public class OrchestrationResponseDTO {

    private String userId;
    private String productId;
    private String orderId;
    private double amount;
    private OrderState state;

    public static class Builder {

        private String userId;
        private String productId;
        private String orderId;
        private double amount;
        private OrderState state;

        public Builder setOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setOrderId(OrderState state) {
            this.state = state;
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

        public OrchestrationResponseDTO build() {
            return new OrchestrationResponseDTO(this);
        }


    }

    private OrchestrationResponseDTO (Builder builder){
        this.orderId = builder.orderId;
        this.userId = builder.userId;
        this.amount = builder.amount;
        this.productId = builder.productId;
        this.state = builder.state;
    }

}
