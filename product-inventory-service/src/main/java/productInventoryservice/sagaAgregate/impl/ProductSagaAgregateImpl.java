package productInventoryservice.sagaAgregate.impl;

import com.angel.models.commands.*;
import com.angel.models.events.*;
import com.angel.models.states.PaymentState;
import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import productInventoryservice.sagaAgregate.api.SagaAgregate;
import productInventoryservice.services.api.ProductInventoryService;

import static com.angel.models.constants.TopicConstants.*;

@Component
@KafkaListener(topics = {PRODUCT_RESERVED_EVENT, PRODUCT_RESERVATION_CANCELED_EVENT}, groupId = GROUP_ID)
public class ProductSagaAgregateImpl implements SagaAgregate {

    @Autowired
    private SendMessage sendService;

    @Autowired
    private ProductInventoryService service;

    @Autowired
    private Factory factory;

    @Override//10
    @KafkaHandler
    public Command handleProductReservationCanceledEvent(ProductReservationCanceledEvent event){

        RejectOrderCommandProduct command = (RejectOrderCommandProduct) this.factory.readEvent(
            PRODUCT_RESERVATION_CANCELED_EVENT,
            REJECT_ORDER_COMMAND_PRODUCT,
            event);

        service.resetQuantity(command.getProductId());
        this.sendService.sendMessage(REJECT_ORDER_COMMAND_PRODUCT, command);
        return command;
    }

    @Override//4
    @KafkaHandler
    public Command handleProductReservedEvent(ProductReservedEvent event){

        ProcessPaymentCommand command = (ProcessPaymentCommand) this.factory
            .readEvent(PRODUCT_RESERVED_EVENT, PROCESS_PAYMENT_COMMAND,
                       event);

        if (!service.isAvailable(command.getProductId(), command.getQuantity())) {

            ProductReservationCancelCommand cancelProdRes = new ProductReservationCancelCommand();
            cancelProdRes.setPaymentState(PaymentState.REJECTED);
            cancelProdRes.setProductId(command.getProductId());
            cancelProdRes.setQuantity(command.getQuantity());
            cancelProdRes.setUserId(command.getUserId());
            cancelProdRes.setPrice(command.getPrice());
            cancelProdRes.setOrderId(command.getOrderId());
            cancelProdRes.setPaymentId("");

            this.sendService.sendMessage(CANCEL_PRODUCT_RESERVATION_COMMAND, cancelProdRes);
            return cancelProdRes;
        }

        service.extractQuantity(command.getProductId(), command.getQuantity());

        this.sendService.sendMessage(PROCESS_PAYMENT_COMMAND, command);
        return command;
    }

}
