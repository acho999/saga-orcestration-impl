package DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import states.InventoryState;

@Getter
@Setter
public class InventoryResponseDTO {

    private int userId;
    private int productId;
    private String orderId;
    private InventoryState state;

    public static class Builder {

        private int userId;
        private int productId;
        private String orderId;
        private InventoryState state;

        public Builder setPrice(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setState(InventoryState state) {
            this.state = state;
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
