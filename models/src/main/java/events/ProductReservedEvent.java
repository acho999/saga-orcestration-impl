package events;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
public class ProductReservedEvent extends Event{

    private String productId;
    private int quantity;

    @Builder
    public ProductReservedEvent(String orderId, String userId, String productId, int quantity){
        super(userId,orderId);
        this.quantity = quantity;
        this.productId = productId;
    }
}
