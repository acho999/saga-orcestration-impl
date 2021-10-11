package com.angel.orderservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import states.OrderState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @Column(name = "Id", unique = true, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String Id;

    @Column(name = "orderState", nullable = false)
    private OrderState orderState;

    @Column(name = "userId", nullable = false)
    private Integer userId;

    @Column(name = "productId", nullable = false)
    private Integer productId;

    @Column(name = "price", nullable = false)
    private Double price;

    public static class Builder{

        private OrderState orderState;
        private Integer userId;
        private Integer productId;
        private Double price;

        public Builder setPrice(Double price){
            this.price = price;
            return this;
        }

        public Builder setProductId(Integer prodId){
            this.productId = prodId;
            return this;
        }

        public Builder setUserId(Integer userId){
            this.userId = userId;
            return this;
        }

        public Builder setOrderState(OrderState state){
            this.orderState = state;
            return this;
        }

        public Order build(){
            return new Order(this);
        }


    }

    public Order() {
    }

    private Order(Builder builder){
        this.price = builder.price;
        this.orderState = builder.orderState;
        this.productId = builder.productId;
        this.userId = builder.userId;
    }
}
