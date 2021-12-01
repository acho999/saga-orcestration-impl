package commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import states.OrderState;

@Getter
@Setter
public class ApproveOrderCommand extends Command{

    private OrderState state;

    @Builder
    public ApproveOrderCommand(OrderState state, String orderId, String userid){
        super(userid, orderId);
        this.state = state;
    }

}
