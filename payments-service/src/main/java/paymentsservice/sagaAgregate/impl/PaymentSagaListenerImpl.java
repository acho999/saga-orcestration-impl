package paymentsservice.sagaAgregate.impl;

import com.angel.models.DTO.PaymentRequestDTO;
import com.angel.models.commands.*;
import com.angel.models.entities.Product;
import com.angel.models.events.*;
import com.angel.models.states.PaymentState;
import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import com.angel.saga.logging.CustomLogging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import paymentsservice.models.Payment;
import paymentsservice.sagaAgregate.api.SagaListener;
import paymentsservice.services.api.PaymentsService;

import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

import static com.angel.models.constants.TopicConstants.*;

@Component
@KafkaListener(topics = {PROCESS_PAYMENT_COMMAND, CANCEL_PAYMENT_COMMAND, GET_PRODUCT_PRICE}, groupId = GROUP_ID)
public class PaymentSagaListenerImpl implements SagaListener {

    private SendMessage sendService;
    private PaymentsService paymentsService;
    private Factory factory;
    private Payment payment;
    private final Class<PaymentSagaListenerImpl> cl = PaymentSagaListenerImpl.class;

    @Autowired
    public PaymentSagaListenerImpl(SendMessage sendService,
                                   PaymentsService paymentsService, Factory factory) {
        this.sendService = sendService;
        this.paymentsService = paymentsService;
        this.factory = factory;
    }

    @KafkaHandler
    private void getProductPrice(Product product){
        CustomLogging.log(cl, product.getPrice() + " " + "from handler in payment");
        this.paymentsService.setProductPrice(product.getPrice());

    }

    @Override//5
    @KafkaHandler
    public Event handleProcessPaymentCommand(ProcessPaymentCommand command){
        Event event = this.factory.readCommand(PROCESS_PAYMENT_COMMAND,
                                               PAYMENT_PROCESSED_EVENT,
                                               command);

        PaymentRequestDTO pmnt = new PaymentRequestDTO();
        pmnt.setUserId(command.getUserId());
        pmnt.setState(PaymentState.APPROVED);
        pmnt.setQuantity(command.getQuantity());
        pmnt.setOrderId(command.getOrderId());
        pmnt.setProductId(command.getProductId());

        this.payment = this.paymentsService.savePayment(command.getUserId(), pmnt);

        if (command != null && PaymentState.REJECTED.equals(this.payment.getState())) {

            PaymentCanceledEvent cancelPayment = new PaymentCanceledEvent();
            cancelPayment.setPaymentState(PaymentState.REJECTED);
            cancelPayment.setUserId(pmnt.getUserId());
            cancelPayment.setProductId(pmnt.getProductId());
            cancelPayment.setQuantity(pmnt.getQuantity());
            cancelPayment.setOrderId(pmnt.getOrderId());
            cancelPayment.setPaymentId(this.payment.getId());

            ProductReservationCanceledEvent cancelProdRes = new ProductReservationCanceledEvent();
            cancelProdRes.setPaymentState(PaymentState.REJECTED);
            cancelProdRes.setProductId(pmnt.getProductId());
            cancelProdRes.setQuantity(pmnt.getQuantity());
            cancelProdRes.setUserId(pmnt.getUserId());
            cancelProdRes.setOrderId(pmnt.getOrderId());
            cancelProdRes.setPaymentId(pmnt.getId());
            cancelProdRes.setPaymentId(this.payment.getId());

            this.sendService.sendMessage(PAYMENT_CANCELED_EVENT, cancelPayment);
            this.sendService.sendMessage(PRODUCT_RESERVATION_CANCELED_EVENT, cancelProdRes);

            return event;

        }
        this.sendService.sendMessage(PAYMENT_PROCESSED_EVENT, event);
        return event;
    }

    @Override//11
    @KafkaHandler
    public void handleCancelPaymentCommand(@Payload CancelPaymentCommand command){
        this.paymentsService.reversePayment(command.getUserId(), command.getPaymentId());
    }

}
