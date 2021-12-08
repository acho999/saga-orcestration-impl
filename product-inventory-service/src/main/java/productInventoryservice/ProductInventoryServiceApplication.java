package productInventoryservice;

import com.angel.models.commands.RejectOrderCommand;
import com.angel.models.commands.ReserveProductCommand;
import com.angel.saga.api.SagaOrchestration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import productInventoryservice.services.api.ProductInventoryService;

@SpringBootApplication(scanBasePackages = {"productInventoryservice"})
public class ProductInventoryServiceApplication {

    @Autowired
    private static SagaOrchestration sagaOrchestration;

    @Autowired
    private static ProductInventoryService service;

    public static void main(String[] args) {
        SpringApplication.run(ProductInventoryServiceApplication.class, args);
        runAll();
    }

    private static void runAll() {
        ReserveProductCommand event =
            (ReserveProductCommand) sagaOrchestration.handleProductReservedEvent();//4

        if (!service.isAvailable(event.getProductId(), event.getQuantity())) {
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
