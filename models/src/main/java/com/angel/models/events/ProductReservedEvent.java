package com.angel.models.events;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
public final class ProductReservedEvent extends Event{
//POJO class should be immutable (without setter and final also is thread safe)
    public ProductReservedEvent(String a){}


    @Builder
    public ProductReservedEvent(String orderId, String userId, String productId, int quantity){
        super(userId,orderId, productId, quantity);
    }
}
