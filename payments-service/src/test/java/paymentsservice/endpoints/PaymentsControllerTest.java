package paymentsservice.endpoints;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import paymentsservice.PaymentsServiceApplication;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PaymentsServiceApplication.class)
@AutoConfigureMockMvc
class PaymentsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createPayment() {
    }

    @Test
    void reversePayment() {
    }

    @Test
    void savePayment() {
    }
}