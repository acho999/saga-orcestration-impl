package com.angel.orderservice.mockMvc;

import com.angel.models.DTO.OrderRequestDTO;
import com.angel.models.DTO.OrderResponseDTO;
import com.angel.models.states.OrderState;
import com.angel.orderservice.endpoints.OrdersController;
import com.angel.orderservice.services.api.OrdersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.angel.models.constants.CommonConstants.FAKE_ORDER_ID;
import static com.angel.models.constants.CommonConstants.FAKE_PRODUCT_ID;
import static com.angel.models.constants.CommonConstants.FAKE_USER_ID;
import static com.angel.models.constants.CommonConstants.QUANTITY;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(controllers = {OrdersController.class})
@AutoConfigureMockMvc
class OrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private OrdersService service;

    @Test
    void createOrder() throws Exception {
        OrderRequestDTO requestDTO = new OrderRequestDTO.Builder()
            .setOrderId(FAKE_ORDER_ID)
            .setOrderState(OrderState.PENDING)
            .setProductId(FAKE_PRODUCT_ID)
            .setUserId(FAKE_USER_ID)
            .setQuantity(QUANTITY).build();

        ResponseEntity<OrderRequestDTO> response = new ResponseEntity<>(requestDTO, HttpStatus.CREATED);
        when(this.service.createOrder(requestDTO)).thenReturn(response.getBody());
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
        OrderResponseDTO responseDTO = new OrderResponseDTO.Builder()
            .setOrderId(FAKE_ORDER_ID)
            .setOrderState(OrderState.PENDING)
            .setProductId(FAKE_PRODUCT_ID)
            .setUserId(FAKE_USER_ID)
            .setQuantity(QUANTITY).build();

        ResponseEntity<OrderResponseDTO> response = new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

        when(this.service.getOrder(FAKE_ORDER_ID)).thenReturn(response.getBody());

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
        when(this.service.cancelOrder(FAKE_ORDER_ID)).thenReturn(true);

        this.mockMvc.perform(post("/orders/cancel/{orderId}",FAKE_ORDER_ID)
                                 .accept(MediaType.APPLICATION_JSON)
                                 .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void approveOrder() throws Exception {
        when(this.service.cancelOrder(FAKE_ORDER_ID)).thenReturn(true);

        this.mockMvc.perform(post("/orders/approve/{orderId}",FAKE_ORDER_ID)
                                 .accept(MediaType.APPLICATION_JSON)
                                 .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}