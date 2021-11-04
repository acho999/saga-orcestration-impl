package com.angel.orderservice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import states.OrderState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @Column(name = "Id", unique = true, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String Id;

    @Column(name = "orderState", nullable = false)
    private OrderState orderState;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "productId", nullable = false)
    private int productId;

    @Column(name = "price", nullable = false)
    private double price;

}
