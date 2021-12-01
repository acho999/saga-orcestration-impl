package events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import states.OrderState;

@Getter
@Setter
public class OrderRejectedEvent extends Event{

    private String reason;
    private OrderState state = OrderState.ORDER_CANCELLED;

    @Builder
    public OrderRejectedEvent(String reason, String userId, String orderId){
        super(userId, orderId);
        this.reason = reason;
    }

}
