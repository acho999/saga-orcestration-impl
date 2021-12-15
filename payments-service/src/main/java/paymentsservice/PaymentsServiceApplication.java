package paymentsservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"paymentsservice", "com.angel.saga.configuration"})
public class PaymentsServiceApplication {


    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(PaymentsServiceApplication.class, args);
    }
}
