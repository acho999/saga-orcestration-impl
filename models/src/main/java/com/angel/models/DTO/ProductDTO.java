package com.angel.models.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
public final class ProductDTO {
//POJO class should be immutable (without setter and final also is thread safe)
    private String id;

    private String name;

    private String description;

    private double price;

    private int quantity;

//    public static class Builder {
//
//        private String id;
//        private String name;
//        private String description;
//        private double price;
//        private int quantity;
//
//        public Builder setDescription(String description) {
//            this.description = description;
//            return this;
//        }
//
//        public Builder setQuantity(int quantity) {
//            this.quantity = quantity;
//            return this;
//        }
//
//        public Builder setName(String name) {
//            this.name = name;
//            return this;
//        }
//
//        public Builder setPrice(double price) {
//            this.price = price;
//            return this;
//        }
//
//        public Builder setId(String id) {
//            this.id = id;
//            return this;
//        }
//
//        public ProductDTO build() {
//            return new ProductDTO(this);
//        }
//
//
//    }
//
//    private ProductDTO(Builder builder){
//        this.description = builder.description;
//        this.id = builder.id;
//        this.name = builder.name;
//        this.price = builder.price;
//        this.quantity = builder.quantity;
//    }


}
