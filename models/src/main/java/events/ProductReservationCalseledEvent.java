package events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductReservationCalseledEvent extends Event{

    private String productId;
    private int quantity;
    private String reason;

    @Builder
    public ProductReservationCalseledEvent(String productId, int quantity, String reason, String userId, String orderId){
        super(userId, orderId);
        this.productId = productId;
        this.quantity = quantity;
        this.reason = reason;
    }

}
