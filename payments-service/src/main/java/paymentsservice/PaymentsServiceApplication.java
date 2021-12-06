package paymentsservice;

import com.angel.saga.api.Saga;
import com.angel.saga.impl.SagaImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"paymentsservice"})
public class PaymentsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentsServiceApplication.class, args);
        Saga saga = new SagaImpl();
        saga.publishProcessPaymentCommand();//5
        saga.handlePaymentProcessedEvent();//6
    }

}
