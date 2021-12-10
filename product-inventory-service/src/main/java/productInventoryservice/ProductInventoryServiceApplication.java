package productInventoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import productInventoryservice.appContext.ApplicationContextUtils;
import productInventoryservice.start.StartClass;

@SpringBootApplication(scanBasePackages = {"productInventoryservice"})
public class ProductInventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductInventoryServiceApplication.class, args);
        ApplicationContext context = ApplicationContextUtils.getApplicationContext();
        StartClass start = context.getBean(StartClass.class);
        start.runAll();
    }
}
