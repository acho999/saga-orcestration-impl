package DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String userId;
    private double balance;

    public static class Builder {

        private String userId;
        private double balance;

        public Builder setBalance(double balance) {
            this.balance = balance;
            return this;
        }

        public Builder setId(String id) {
            this.userId = id;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(this);
        }


    }

    private UserDTO(Builder builder){
        this.userId = builder.userId;
        this.balance = builder.balance;
    }


}
