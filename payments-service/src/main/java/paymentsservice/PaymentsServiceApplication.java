package paymentsservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import paymentsservice.appContext.ApplicationContextUtils;
import paymentsservice.start.StartClass;

@SpringBootApplication(scanBasePackages = {"paymentsservice"})
public class PaymentsServiceApplication {


    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(PaymentsServiceApplication.class, args);
        ApplicationContext utils = ApplicationContextUtils.getApplicationContext();
        StartClass start = utils.getBean(StartClass.class);
        start.runAll();
    }
}
