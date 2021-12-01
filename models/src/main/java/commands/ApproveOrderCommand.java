package commands;

import lombok.Builder;
import states.OrderState;

public class ApproveOrderCommand extends Command{

    private OrderState state;

    @Builder
    public ApproveOrderCommand(OrderState state, String orderId, String userid){
        super(userid, orderId);
        this.state = state;
    }

}
