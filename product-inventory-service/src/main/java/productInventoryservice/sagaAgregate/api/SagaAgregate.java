package productInventoryservice.sagaAgregate.api;

import com.angel.models.commands.Command;
import com.angel.models.events.ProductReservationCanceledEvent;
import com.angel.models.events.ProductReservedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface SagaAgregate {

    Command handleProductReservationCanceledEvent(ProductReservationCanceledEvent message)
        throws JsonProcessingException;
    Command handleProductReservedEvent(ProductReservedEvent message)
        throws JsonProcessingException;
}
