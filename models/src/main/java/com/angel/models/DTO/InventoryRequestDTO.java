package com.angel.models.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryRequestDTO {

    private String userId;
    private String productId;
    private String orderId;

    public static class Builder {

        private String userId;
        private String productId;
        private String orderId;

        public Builder setOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setProductId(String prodId) {
            this.productId = prodId;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public InventoryRequestDTO build() {
            return new InventoryRequestDTO(this);
        }


    }

    private InventoryRequestDTO(Builder builder){
        this.orderId = builder.orderId;
        this.userId = builder.userId;
        this.productId = builder.productId;
    }

}
