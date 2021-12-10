package productInventoryservice.start;

import com.angel.models.commands.RejectOrderCommand;
import com.angel.models.commands.ReserveProductCommand;
import com.angel.saga.api.SagaOrchestration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import productInventoryservice.services.api.ProductInventoryService;

@Component
public class StartClass {


    @Autowired
    private SagaOrchestration sagaOrchestration;

    @Autowired
    private ProductInventoryService service;



    public void runAll() {
        ReserveProductCommand event =
            (ReserveProductCommand) sagaOrchestration.handleProductReservedEvent();//4

        if (event != null && !service.isAvailable(event.getProductId(), event.getQuantity())) {
            sagaOrchestration.publishCancelProductReservationCommand();//9
            sagaOrchestration.publishCancelPaymentCommand();//11
        }

        RejectOrderCommand command =
            (RejectOrderCommand) sagaOrchestration.handleProductReservationCanceledEvent();//10
        if (command != null) {
            service.resetQuantity(command.getProductId());
            return;
        }
        if (event != null) {
            service.isAvailable(event.getProductId(), event.getQuantity());
        }
    }

}
