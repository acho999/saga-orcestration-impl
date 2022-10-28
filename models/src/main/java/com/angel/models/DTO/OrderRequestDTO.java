package com.angel.models.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.angel.models.states.OrderState;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Data//this should be here in order for test to be deserialized
public class OrderRequestDTO implements Serializable {

    private String orderId;
    private OrderState orderState;
    private String userId;
    private String productId;
    private int quantity;

    //@JsonIgnoreType
    public static class Builder{

        private String userId;
        private String productId;
        private OrderState orderState;
        private String orderId;
        private int quantity;

        public Builder setUserId(String userId){
            this.userId = userId;
            return this;
        }

        public Builder setQuantity(int quantity){
            this.quantity = quantity;
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
            this.orderState = state;
            return this;
        }

        public OrderRequestDTO build(){
            return new OrderRequestDTO(this);
        }

    }

    private OrderRequestDTO(Builder builder){
        this.orderId = builder.orderId;
        this.userId = builder.userId;
        this.productId = builder.productId;
        this.orderState = builder.orderState;
        this.quantity = builder.quantity;
    }

}
