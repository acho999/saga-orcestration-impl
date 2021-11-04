package DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String id;
    private int amount;

    public static class Builder {

        private String id;
        private int balance;

        public Builder setAmount(int amount) {
            this.balance = amount;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(this);
        }


    }

    private UserDTO(Builder builder){
        this.id = builder.id;
        this.amount = builder.balance;
    }


}
