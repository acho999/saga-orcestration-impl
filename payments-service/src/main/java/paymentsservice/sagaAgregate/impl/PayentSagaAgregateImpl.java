package paymentsservice.sagaAgregate.impl;

import com.angel.models.DTO.PaymentRequestDTO;
import com.angel.models.commands.*;
import com.angel.models.events.*;
import com.angel.models.states.PaymentState;
import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import paymentsservice.models.Payment;
import paymentsservice.sagaAgregate.api.SagaAgregate;
import paymentsservice.services.api.PaymentsService;

import static com.angel.models.constants.TopicConstants.*;

@Component
@KafkaListener(topics = {PROCESS_PAYMENT_COMMAND, CANCEL_PAYMENT_COMMAND}, groupId = GROUP_ID)
public class PayentSagaAgregateImpl implements SagaAgregate {

    @Autowired
    private SendMessage sendService;

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    private Factory factory;

    private Payment payment;

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
        pmnt.setPrice(command.getPrice());

        this.payment = this.paymentsService.savePayment(command.getUserId(), pmnt);

        if (command != null && this.payment.getState().equals(PaymentState.REJECTED)) {

            PaymentCanceledEvent cancelPayment = new PaymentCanceledEvent();
            cancelPayment.setPaymentState(pmnt.getState());
            cancelPayment.setUserId(pmnt.getUserId());
            cancelPayment.setProductId(pmnt.getProductId());
            cancelPayment.setQuantity(pmnt.getQuantity());
            cancelPayment.setOrderId(pmnt.getOrderId());
            cancelPayment.setPrice(pmnt.getPrice());
            cancelPayment.setPaymentId(this.payment.getId());

            ProductReservationCanceledEvent cancelProdRes = new ProductReservationCanceledEvent();
            cancelProdRes.setPaymentState(PaymentState.REJECTED);
            cancelProdRes.setProductId(pmnt.getProductId());
            cancelProdRes.setQuantity(pmnt.getQuantity());
            cancelProdRes.setUserId(pmnt.getUserId());
            cancelProdRes.setPrice(pmnt.getPrice());
            cancelProdRes.setOrderId(pmnt.getOrderId());
            cancelProdRes.setPaymentId(pmnt.getId());
            cancelProdRes.setPaymentId(this.payment.getId());

            this.sendService.sendMessage(PAYMENT_CANCELED_EVENT, cancelPayment);
            this.sendService.sendMessage(PRODUCT_RESERVATION_CANCELED_EVENT, cancelProdRes);

            return null;

        }
        this.sendService.sendMessage(PAYMENT_PROCESSED_EVENT, event);
        return event;
    }

    @Override//11
    @KafkaHandler
    public void handleCancelPaymentCommand(CancelPaymentCommand command){
        this.paymentsService.reversePayment(command.getUserId(), command.getPaymentId());
    }

}
