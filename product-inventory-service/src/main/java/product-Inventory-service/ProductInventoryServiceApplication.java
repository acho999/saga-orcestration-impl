package paymentsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"paymentsInventoryservice"})
public class ProductInventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductInventoryServiceApplication.class, args);
    }

}
