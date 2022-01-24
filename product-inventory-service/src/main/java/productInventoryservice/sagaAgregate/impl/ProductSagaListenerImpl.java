package productInventoryservice.sagaAgregate.impl;

import com.angel.models.DTO.ProductDTO;
import com.angel.models.commands.*;
import com.angel.models.entities.Product;
import com.angel.models.events.*;
import com.angel.models.states.PaymentState;
import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import productInventoryservice.sagaAgregate.api.SagaListener;
import productInventoryservice.services.api.ProductInventoryService;
import productInventoryservice.services.impl.ProductInventoryServiceImpl;

import java.util.logging.Logger;

import static com.angel.models.constants.TopicConstants.*;

@Component
@KafkaListener(topics = {RESERVE_PRODUCT_COMMAND, CANCEL_PRODUCT_RESERVATION_COMMAND, SET_PRODUCT_PRICE}, groupId = GROUP_ID)
public class ProductSagaListenerImpl implements SagaListener {

    @Autowired
    private SendMessage sendService;

    @Autowired
    private ProductInventoryService service;

    @Autowired
    private Factory factory;

    private static final Logger logger = Logger.getLogger(ProductInventoryServiceImpl.class.getSimpleName());


    @Override//9
    @KafkaHandler
    public void handleCancelProductReservationCommand(@Payload ProductReservationCancelCommand command){
        service.resetQuantity(command.getProductId(), command.getQuantity(), command.getPaymentState());;
    }

    @Override//4
    @KafkaHandler
    public Event handleReserveProductCommand(@Payload ReserveProductCommand command){
        Event event = this.factory.readCommand(RESERVE_PRODUCT_COMMAND,
                                               PRODUCT_RESERVED_EVENT,
                                               command);
        if (!service.isAvailable(command.getProductId(), command.getQuantity())) {

            ProductReservationCanceledEvent cancelProdRes = new ProductReservationCanceledEvent();
            cancelProdRes.setPaymentState(PaymentState.REJECTED);
            cancelProdRes.setProductId(command.getProductId());
            cancelProdRes.setQuantity(command.getQuantity());
            cancelProdRes.setUserId(command.getUserId());
            cancelProdRes.setOrderId(command.getOrderId());
            cancelProdRes.setPaymentId("");

            this.sendService.sendMessage(PRODUCT_RESERVATION_CANCELED_EVENT, cancelProdRes);
            return event;
        }

        service.extractQuantity(command.getProductId(), command.getQuantity());
        this.sendService.sendMessage(PRODUCT_RESERVED_EVENT, event);
        return event;
    }

    @KafkaHandler
    public void handleProductPriceEvent(@Payload Product product){
        ProductDTO prod = this.service.getProduct(product.getId());
        product.setPrice(prod.getPrice());
        logger.info(String.valueOf(product.getPrice() + " " + "from product inventory"));
        this.sendService.sendMessage(GET_PRODUCT_PRICE, product);
    }

}
