package paymentsservice.sagaAgregate.api;

import com.angel.models.commands.CancelPaymentCommand;
import com.angel.models.commands.Command;
import com.angel.models.commands.ProcessPaymentCommand;
import com.angel.models.events.Event;
import com.angel.models.events.PaymentCanceledEvent;
import com.angel.models.events.PaymentProcessedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Properties;

public interface SagaAgregate {


    Event handleProcessPaymentCommand(ProcessPaymentCommand command);

    void handleCancelPaymentCommand(CancelPaymentCommand message);

}
