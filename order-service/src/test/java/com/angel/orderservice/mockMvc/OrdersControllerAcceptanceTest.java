package com.angel.orderservice.mockMvc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class OrdersControllerAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createOrder() {
    }

    @Test
    void getOrder() {
    }

    @Test
    void cancelOrder() {
    }

    @Test
    void approveOrder() {
    }

}
