package com.angel.models.DTO;

import lombok.Getter;
import lombok.Setter;
import com.angel.models.states.OrderState;

@Getter
@Setter
public class OrderResponseDTO {

    private String userId;
    private String productId;
    private String orderId;
    private OrderState state;
    private int quantity;

    public static class Builder{

        private String userId;
        private String productId;
        private String orderId;
        private OrderState state;
        private int quantity;

        public Builder setUserId(String userId){
            this.userId = userId;
            return this;
        }

        public Builder setProductId(String productId){
            this.productId = productId;
            return this;
        }

        public Builder setOrderId(String orderId){
            this.orderId = orderId;
            return this;
        }

        public Builder setOrderState(OrderState state){
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
