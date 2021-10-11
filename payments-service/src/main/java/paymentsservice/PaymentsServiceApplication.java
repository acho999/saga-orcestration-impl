package paymentsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"paymentsservice"})
public class PaymentsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentsServiceApplication.class, args);
    }

}
