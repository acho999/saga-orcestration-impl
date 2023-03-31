package com.angel.models.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public final class UserDTO {
//POJO class should be immutable (without setter and final also is thread safe)
    private String userId;
    private double balance;
    private List<?> payments;
//
//    public static class Builder {
//
//        private String userId;
//        private double balance;
//
//        public Builder setBalance(double balance) {
//            this.balance = balance;
//            return this;
//        }
//
//        public Builder setId(String id) {
//            this.userId = id;
//            return this;
//        }
//
//        public UserDTO build() {
//            return new UserDTO(this);
//        }
//
//
//    }
//
//    private UserDTO(Builder builder){
//        this.userId = builder.userId;
//        this.balance = builder.balance;
//    }


}
