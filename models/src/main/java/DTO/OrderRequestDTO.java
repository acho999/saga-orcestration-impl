package DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO {

    private int userId;
    private int productId;
    private String orderId;

    public static class Builder{

        private int userId;
        private int productId;
        private String orderId;

        public Builder setUserId(Integer userId){
            this.userId = userId;
            return this;
        }

        public Builder setProductId(int productId){
            this.productId = productId;
            return this;
        }

        public Builder setOrderId(String orderId){
            this.orderId = orderId;
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
    }


}
