package paymentsservice.sagaAgregate.api;

import com.angel.models.commands.Command;
import com.angel.models.events.Event;
import com.angel.models.events.PaymentCanceledEvent;
import com.angel.models.events.PaymentProcessedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Properties;

public interface SagaAgregate {


    Command handlePaymentProcessedEvent(PaymentProcessedEvent message)
        throws JsonProcessingException;

    Command handlePaymentCanceledEvent(PaymentCanceledEvent message)
        throws JsonProcessingException;

}
