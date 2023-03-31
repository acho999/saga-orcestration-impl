package com.angel.orderservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import com.angel.models.states.OrderState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Order {

    @Id
    @Column(name = "orderId", unique = true, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String orderId;

    @Column(name = "orderState", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderState orderState;

    @Column(name = "userId", nullable = false)
    private String userId;

    @Column(name = "productId", nullable = false)
    private String productId;

    @Column(name = "qty", nullable = false)
    private int qty;

}
