package productInventoryservice.sagaAgregate.impl;

import com.angel.models.DTO.ProductDTO;
import com.angel.models.commands.ProductReservationCancelCommand;
import com.angel.models.commands.ReserveProductCommand;
import com.angel.models.entities.Product;
import com.angel.models.events.Event;
import com.angel.models.events.ProductReservationCanceledEvent;
import com.angel.models.states.PaymentState;
import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import com.angel.saga.logging.CustomLogging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import productInventoryservice.sagaAgregate.api.SagaListener;
import productInventoryservice.services.api.ProductInventoryService;

import static com.angel.models.constants.TopicConstants.*;

@Component
@KafkaListener(topics = {RESERVE_PRODUCT_COMMAND, CANCEL_PRODUCT_RESERVATION_COMMAND, SET_PRODUCT_PRICE}, groupId = GROUP_ID)
public class ProductSagaListenerImpl implements SagaListener {

    private SendMessage sendService;
    private ProductInventoryService service;
    private Factory factory;
    private final Class<ProductSagaListenerImpl> cl = ProductSagaListenerImpl.class;
    @Autowired
    public ProductSagaListenerImpl(SendMessage sendService,
                                   ProductInventoryService service, Factory factory) {
        this.sendService = sendService;
        this.service = service;
        this.factory = factory;
    }

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
        CustomLogging.log(cl,product.getPrice() + " " + "from product inventory");
        this.sendService.sendMessage(GET_PRODUCT_PRICE, product);
    }

}
