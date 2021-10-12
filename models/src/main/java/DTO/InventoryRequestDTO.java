package DTO;

import lombok.Getter;
import lombok.Setter;
import states.OrderState;

@Getter
@Setter
public class InventoryRequestDTO {

    private Integer userId;
    private Integer productId;
    private String orderId;

    public static class Builder {

        private Integer userId;
        private Integer productId;
        private String orderId;

        public Builder setPrice(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setProductId(Integer prodId) {
            this.productId = prodId;
            return this;
        }

        public Builder setUserId(Integer userId) {
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
