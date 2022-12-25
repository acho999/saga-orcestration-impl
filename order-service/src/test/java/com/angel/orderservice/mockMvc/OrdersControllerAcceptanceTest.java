package com.angel.orderservice.mockMvc;

import com.angel.models.DTO.OrderRequestDTO;
import com.angel.models.states.OrderState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.angel.models.constants.CommonConstants.FAKE_ORDER_ID;
import static com.angel.models.constants.CommonConstants.FAKE_PRODUCT_ID;
import static com.angel.models.constants.CommonConstants.FAKE_USER_ID;
import static com.angel.models.constants.CommonConstants.QUANTITY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrdersControllerAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createOrder() throws Exception {
        OrderRequestDTO requestDTO = new OrderRequestDTO.Builder()
            .setOrderState(OrderState.PENDING)
            .setProductId(FAKE_PRODUCT_ID)
            .setUserId(FAKE_USER_ID)
            .setQuantity(QUANTITY).build();

        this.mockMvc.perform(post("/orders/create")
                                 .accept(MediaType.APPLICATION_JSON)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(this.mapper.writeValueAsBytes(requestDTO))
            ).andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andExpect(MockMvcResultMatchers.jsonPath("$.orderId", Matchers.equalTo(FAKE_ORDER_ID)));
    }

    @Test
    void getOrder() throws Exception {
        this.mockMvc.perform(get("/orders/getOrder/{orderId}",FAKE_ORDER_ID)
                                 .accept(MediaType.APPLICATION_JSON)
                                 .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andExpect(MockMvcResultMatchers.jsonPath("$.orderId", Matchers.equalTo(FAKE_ORDER_ID)));
    }

    @Test
    void cancelOrder() throws Exception {
        this.mockMvc.perform(post("/orders/cancel/{orderId}",FAKE_ORDER_ID)
                                 .accept(MediaType.APPLICATION_JSON)
                                 .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void approveOrder() throws Exception {
        this.mockMvc.perform(post("/orders/approve/{orderId}",FAKE_ORDER_ID)
                                 .accept(MediaType.APPLICATION_JSON)
                                 .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

}
