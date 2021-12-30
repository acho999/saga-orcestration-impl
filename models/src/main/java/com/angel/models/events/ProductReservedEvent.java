package com.angel.models.events;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ProductReservedEvent extends Event{

    public ProductReservedEvent(String a){}


    @Builder
    public ProductReservedEvent(String orderId, String userId, String productId, int quantity,double price){
        super(userId,orderId, price, productId, quantity);
    }
}
