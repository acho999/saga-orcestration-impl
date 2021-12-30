package com.angel.models.DTO;

import lombok.Getter;
import lombok.Setter;
import com.angel.models.states.OrderState;

@Getter
@Setter
public class OrderRequestDTO {

    private String id;
    private OrderState orderState;
    private String userId;
    private String productId;
    private int quantity;
    private double price;

//    public static class Builder{
//
//        private String userId;
//        private String productId;
//        private OrderState orderState;
//        private String id;
//        private int quantity;
//
//        public Builder setUserId(String userId){
//            this.userId = userId;
//            return this;
//        }
//
//        public Builder setQuantity(int quantity){
//            this.quantity = quantity;
//            return this;
//        }
//
//        public Builder setProductId(String productId){
//            this.productId = productId;
//            return this;
//        }
//
//        public Builder setOrderId(String id){
//            this.id = id;
//            return this;
//        }
//
//        public Builder setOrderState(OrderState state){
//            this.orderState = state;
//            return this;
//        }
//
//        public OrderRequestDTO build(){
//            return new OrderRequestDTO(this);
//        }
//
//    }
//
//    private OrderRequestDTO(Builder builder){
//        this.id = builder.id;
//        this.userId = builder.userId;
//        this.productId = builder.productId;
//        this.orderState = builder.orderState;
//    }

}
