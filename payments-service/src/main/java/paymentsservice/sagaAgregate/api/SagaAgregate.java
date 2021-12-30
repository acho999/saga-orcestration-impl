package paymentsservice.sagaAgregate.api;

import com.angel.models.commands.Command;
import com.angel.models.events.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Properties;

public interface SagaAgregate {


    Command handlePaymentProcessedEvent(String message)
        throws JsonProcessingException;

    Command handlePaymentCanceledEvent(String message)
        throws JsonProcessingException;

}
