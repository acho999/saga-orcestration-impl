package productInventoryservice.sagaAgregate.impl;

import com.angel.models.commands.*;
import com.angel.models.events.*;
import com.angel.models.states.PaymentState;
import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import productInventoryservice.sagaAgregate.api.SagaAgregate;
import productInventoryservice.services.api.ProductInventoryService;

import static com.angel.models.constants.TopicConstants.*;

@Component
public class ProductSagaAgregateImpl implements SagaAgregate {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SendMessage sendService;

    @Autowired
    private ProductInventoryService service;

    private static final String droupId = GROUP_ID;

    @Autowired
    private Factory factory;

    @Override//10
    @KafkaListener(topics = PRODUCT_RESERVATION_CANCELED_EVENT, groupId = droupId)
    public Command handleProductReservationCanceledEvent(String message)
        throws JsonProcessingException {
        System.out.println("handleProductReservationCanceledEvent");
        RejectOrderCommand command = (RejectOrderCommand) this.factory.readEvent(
            PRODUCT_RESERVATION_CANCELED_EVENT,
            REJECT_ORDER_COMMAND_PRODUCT,
            new ProductReservationCalseledEvent(), message);

        service.resetQuantity(command.getProductId());
        this.sendService.sendMessage(REJECT_ORDER_COMMAND_PRODUCT, command, this.mapper);
        return command;
    }

    @Override//4
    @KafkaListener(topics = PRODUCT_RESERVED_EVENT, groupId = droupId)
    public Command handleProductReservedEvent(String message)
        throws JsonProcessingException {
        System.out.println("handleProductReservedEvent");
        ProcessPaymentCommand command = (ProcessPaymentCommand) this.factory
            .readEvent(PRODUCT_RESERVED_EVENT, PROCESS_PAYMENT_COMMAND,
                       new ProductReservedEvent(), message);

        if (!service.isAvailable(command.getProductId(), command.getQuantity())) {

            ProductReservationCancelCommand cancelProdRes = new ProductReservationCancelCommand();
            cancelProdRes.setPaymentState(PaymentState.PAYMENT_REJECTED);
            cancelProdRes.setProductId(command.getProductId());
            cancelProdRes.setQuantity(command.getQuantity());
            cancelProdRes.setUserId(command.getUserId());
            cancelProdRes.setPrice(command.getPrice());
            cancelProdRes.setOrderId(command.getOrderId());
            cancelProdRes.setPaymentId("");

            this.sendService.sendMessage(CANCEL_PRODUCT_RESERVATION_COMMAND, cancelProdRes,
                                         this.mapper);
            return null;
        }

        service.extractQuantity(command.getProductId(), command.getQuantity());

        this.sendService.sendMessage(PROCESS_PAYMENT_COMMAND, command, this.mapper);
        return command;
    }

}
