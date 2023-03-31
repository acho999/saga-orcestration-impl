package com.angel.models.DTO;

import lombok.Getter;
import lombok.Setter;
import com.angel.models.states.InventoryState;

@Getter
public final class InventoryResponseDTO {
//POJO class should be immutable (without setter and final also is thread safe)
    private String userId;
    private String productId;
    private String orderId;
    private InventoryState state;

    public static class Builder {

        private String userId;
        private String productId;
        private String orderId;
        private InventoryState state;

        public Builder setOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setState(InventoryState state) {
            this.state = state;
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

        public InventoryResponseDTO build() {
            return new InventoryResponseDTO(this);
        }


    }

    private InventoryResponseDTO(Builder builder){
        this.orderId = builder.orderId;
        this.userId = builder.userId;
        this.productId = builder.productId;
        this.state = builder.state;
    }


}
