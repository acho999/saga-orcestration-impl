package productInventoryservice;

import com.angel.saga.api.Saga;
import com.angel.saga.impl.SagaImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"productInventoryservice"})
public class ProductInventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductInventoryServiceApplication.class, args);
        Saga saga = new SagaImpl();
        saga.publishReserveProductCommand();//3
        saga.handleProductReservedEvent();//4
    }
}
