package DTO;

public class OrchestrationRequestDTO {

    private Integer userId;
    private Integer productId;
    private String orderId;
    private Double amount;

    public static class Builder {

        private Integer userId;
        private String orderId;
        private Double amount;
        private Integer productId;

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
