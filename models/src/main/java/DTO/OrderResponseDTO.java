package DTO;

import states.OrderState;

public class OrderResponseDTO {

    private Integer userId;
    private Integer productId;
    private String orderId;
    private OrderState state;
    private Integer quantity;

    public static class Builder{

        private Integer userId;
        private Integer productId;
        private String orderId;
        private OrderState state;
        private Integer quantity;

        public Builder setUserId(Integer userId){
            this.userId = userId;
            return this;
        }

        public Builder setProductId(Integer productId){
            this.productId = productId;
            return this;
        }

        public Builder setOrderId(String orderId){
            this.userId = userId;
            return this;
        }

        public Builder setUserId(OrderState state){
            this.state = state;
            return this;
        }

        public Builder setQuantity(Integer quantity){
            this.quantity = quantity;
            return this;
        }

        public OrderResponseDTO build(){
            return new OrderResponseDTO(this);
        }

    }

    private OrderResponseDTO(Builder builder){
        this.orderId = builder.orderId;
        this.userId = builder.userId;
        this.quantity = builder.quantity;
        this.state = builder.state;
        this.productId = builder.productId;
    }

}
