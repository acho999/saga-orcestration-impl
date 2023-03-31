package com.angel.models.commands;

import com.angel.models.states.PaymentState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ProductReservationCancelCommand extends Command{
//POJO class should be immutable (without setter and final also is thread safe)
    private String reason;
    private String paymentId;
    private PaymentState paymentState;

    public ProductReservationCancelCommand(String a){}


    @Builder
    public ProductReservationCancelCommand(String paymentId,PaymentState paymentState , String productId, int quantity, String reason, String userId, String orderId){
        super(userId, orderId, productId, quantity);
        this.paymentId = paymentId;
        this.paymentState = paymentState;
        this.reason = reason;
    }

}
