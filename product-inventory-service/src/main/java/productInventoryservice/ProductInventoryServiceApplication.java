package productInventoryservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import productInventoryservice.appContext.ApplicationContextUtils;
import productInventoryservice.start.StartClass;

@SpringBootApplication(scanBasePackages = {"productInventoryservice"})
public class ProductInventoryServiceApplication {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(ProductInventoryServiceApplication.class, args);
        ApplicationContext context = ApplicationContextUtils.getApplicationContext();
        StartClass start = context.getBean(StartClass.class);
        start.runAll();
//        Thread thread = new Thread(()->{
//            try {
//                start.runAll();
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//        });
//
//        thread.start();

    }
}
