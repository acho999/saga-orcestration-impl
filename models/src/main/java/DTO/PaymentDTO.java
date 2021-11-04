package DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {

    private String id;
    private int amount;

    public static class Builder {

        private String id;
        private int amount;

        public Builder setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public PaymentDTO build() {
            return new PaymentDTO(this);
        }


    }

    private PaymentDTO(Builder builder){
        this.id = builder.id;
        this.amount = builder.amount;
    }


}
