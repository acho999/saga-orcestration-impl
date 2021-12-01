package events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import states.OrderState;

@Getter
@Setter
public class OrderApprovedEvent extends Event{

    private OrderState state;

    @Builder
    public OrderApprovedEvent(OrderState state, String orderId, String userid){
        super(userid, orderId);
        this.state = state;
    }

}
