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
@KafkaListener(topics = {PAYMENT_PROCESSED_EVENT, PAYMENT_CANCELED_EVENT}, groupId = GROUP_ID)
public class PayentSagaAgregateImpl implements SagaAgregate {

    @Autowired
    private SendMessage sendService;

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    private Factory factory;

    private Payment payment;

    @Override//6
    @KafkaHandler
    public Command handlePaymentProcessedEvent(PaymentProcessedEvent event){
        ApproveOrderCommand command = (ApproveOrderCommand) this.factory
            .readEvent(PAYMENT_PROCESSED_EVENT,
                       APPROVE_ORDER_COMMAND,
                       event);

        PaymentRequestDTO pmnt = new PaymentRequestDTO();
        pmnt.setUserId(command.getUserId());
        pmnt.setState(PaymentState.APPROVED);
        pmnt.setQuantity(command.getQuantity());
        pmnt.setOrderId(command.getOrderId());
        pmnt.setProductId(command.getProductId());
        pmnt.setPrice(command.getPrice());

        this.payment = this.paymentsService.savePayment(command.getUserId(), pmnt);

        if (command != null && this.payment.getState().equals(PaymentState.REJECTED)) {

            CancelPaymentCommand cancelPayment = new CancelPaymentCommand();
            cancelPayment.setPaymentState(pmnt.getState());
            cancelPayment.setUserId(pmnt.getUserId());
            cancelPayment.setProductId(pmnt.getProductId());
            cancelPayment.setQuantity(pmnt.getQuantity());
            cancelPayment.setOrderId(pmnt.getOrderId());
            cancelPayment.setPrice(pmnt.getPrice());
            cancelPayment.setPaymentId(this.payment.getId());

            ProductReservationCancelCommand cancelProdRes = new ProductReservationCancelCommand();
            cancelProdRes.setPaymentState(PaymentState.REJECTED);
            cancelProdRes.setProductId(pmnt.getProductId());
            cancelProdRes.setQuantity(pmnt.getQuantity());
            cancelProdRes.setUserId(pmnt.getUserId());
            cancelProdRes.setPrice(pmnt.getPrice());
            cancelProdRes.setOrderId(pmnt.getOrderId());
            cancelProdRes.setPaymentId(pmnt.getId());
            cancelProdRes.setPaymentId(this.payment.getId());

            this.sendService.sendMessage(CANCEL_PAYMENT_COMMAND, cancelPayment);
            this.sendService.sendMessage(CANCEL_PRODUCT_RESERVATION_COMMAND, cancelProdRes);

            return cancelPayment;

        }
        this.sendService.sendMessage(APPROVE_ORDER_COMMAND, command);
        return command;
    }

    @Override//12
    @KafkaHandler
    public Command handlePaymentCanceledEvent(PaymentCanceledEvent event){
        RejectOrderCommandProduct command =
            (RejectOrderCommandProduct) this.factory.readEvent(PAYMENT_CANCELED_EVENT
                , REJECT_ORDER_COMMAND_PAYMENT, event);

        this.sendService.sendMessage(REJECT_ORDER_COMMAND_PAYMENT, command);
        this.paymentsService.reversePayment(command.getUserId(), command.getPaymentId());
        return command;
    }

}
