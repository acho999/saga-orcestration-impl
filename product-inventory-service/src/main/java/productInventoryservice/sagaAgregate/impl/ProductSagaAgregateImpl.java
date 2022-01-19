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
@KafkaListener(topics = {RESERVE_PRODUCT_COMMAND, CANCEL_PRODUCT_RESERVATION_COMMAND}, groupId = GROUP_ID)
public class ProductSagaAgregateImpl implements SagaAgregate {

    @Autowired
    private SendMessage sendService;

    @Autowired
    private ProductInventoryService service;

    @Autowired
    private Factory factory;

    @Override//9
    @KafkaHandler
    public void handleCancelProductReservationCommand(ProductReservationCancelCommand command){
        service.resetQuantity(command.getProductId(), command.getQuantity(), command.getPaymentState());;
    }

    @Override//4
    @KafkaHandler
    public Event handleReserveProductCommand(ReserveProductCommand command){
        Event event = this.factory.readCommand(RESERVE_PRODUCT_COMMAND,
                                               PRODUCT_RESERVED_EVENT,
                                               command);
        if (!service.isAvailable(command.getProductId(), command.getQuantity())) {

            ProductReservationCanceledEvent cancelProdRes = new ProductReservationCanceledEvent();
            cancelProdRes.setPaymentState(PaymentState.REJECTED);
            cancelProdRes.setProductId(command.getProductId());
            cancelProdRes.setQuantity(command.getQuantity());
            cancelProdRes.setUserId(command.getUserId());
            cancelProdRes.setPrice(command.getPrice());
            cancelProdRes.setOrderId(command.getOrderId());
            cancelProdRes.setPaymentId("");

            this.sendService.sendMessage(PRODUCT_RESERVATION_CANCELED_EVENT, cancelProdRes);
            return null;
        }

        service.extractQuantity(command.getProductId(), command.getQuantity());
        this.sendService.sendMessage(PRODUCT_RESERVED_EVENT, event);
        return event;
    }

}
