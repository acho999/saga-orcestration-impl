package com.angel.orderservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import states.OrderState;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    @Enumerated(value = EnumType.STRING)
    private OrderState orderState;

    @Column(name = "userId", nullable = false)
    private String userId;

    @Column(name = "productId", nullable = false)
    private String productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;



}
