package productInventoryservice.sagaAgregate.api;

import com.angel.models.commands.ProductReservationCancelCommand;
import com.angel.models.commands.ReserveProductCommand;
import com.angel.models.events.Event;

public interface SagaListener {

    void handleCancelProductReservationCommand(ProductReservationCancelCommand command);
    Event handleReserveProductCommand(ReserveProductCommand command);
}
