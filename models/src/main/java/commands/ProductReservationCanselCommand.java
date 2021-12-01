package commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductReservationCanselCommand extends Command{

    private String productId;
    private int quantity;
    private String reason;

    @Builder
    public ProductReservationCanselCommand(String productId, int quantity, String reason, String userId, String orderId){
        super(userId, orderId);
        this.productId = productId;
        this.quantity = quantity;
        this.reason = reason;
    }

}
