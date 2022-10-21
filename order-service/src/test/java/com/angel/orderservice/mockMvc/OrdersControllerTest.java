package com.angel.orderservice.mockMvc;

import com.angel.orderservice.endpoints.OrdersController;
import com.angel.orderservice.services.api.OrdersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = {OrdersController.class})
@AutoConfigureMockMvc
class OrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdersService service;

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