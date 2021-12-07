package productInventoryservice;

import com.angel.models.commands.RejectOrderCommand;
import com.angel.models.commands.ReserveProductCommand;
import com.angel.models.events.Event;
import com.angel.models.events.ProductReservationCalseledEvent;
import com.angel.saga.api.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import productInventoryservice.services.api.ProductInventoryService;

@SpringBootApplication(scanBasePackages = {"productInventoryservice"})
public class ProductInventoryServiceApplication {

    @Autowired
    private static  Saga saga ;

    @Autowired
    private static ProductInventoryService service ;

    public static void main(String[] args) {
        SpringApplication.run(ProductInventoryServiceApplication.class, args);


        ReserveProductCommand event = (ReserveProductCommand)saga.handleProductReservedEvent();//4

        if(!service.isAvailable(event.getProductId(), event.getQuantity())){
            saga.publishCancelProductReservationCommand();//9
            return;
        }

        RejectOrderCommand command =(RejectOrderCommand)saga.handleProductReservationCanceledEvent();//10
        if(command != null){
            service.resetQuantity(command.getProductId());
            return;
        }
        if (event != null){
            service.isAvailable(event.getProductId(), event.getQuantity());
        }


    }

}
