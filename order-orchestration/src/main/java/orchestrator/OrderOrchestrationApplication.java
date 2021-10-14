package orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"orchestrator"})
public class OrderOrchestrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderOrchestrationApplication.class, args);
    }
}
