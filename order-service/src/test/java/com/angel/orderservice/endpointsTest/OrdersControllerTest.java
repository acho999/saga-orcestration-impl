package com.angel.orderservice.endpointsTest;

import com.angel.models.DTO.OrderRequestDTO;
import com.angel.models.DTO.OrderResponseDTO;
import com.angel.models.states.OrderState;
import com.angel.orderservice.endpoints.OrdersController;
import com.angel.orderservice.exceptions.NotFoundException;
import com.angel.orderservice.models.Order;
import com.angel.orderservice.services.api.OrdersService;
import com.angel.orderservice.services.api.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.angel.models.constants.CommonConstants.FAKE_ORDER_ID;
import static com.angel.models.constants.CommonConstants.FAKE_PRODUCT_ID;
import static com.angel.models.constants.CommonConstants.FAKE_USER_ID;
import static com.angel.models.constants.CommonConstants.NOT_FOUND;
import static com.angel.models.constants.CommonConstants.QUANTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrdersControllerTest {

    @InjectMocks
    private OrdersController controller;
    @Mock private OrdersService service;
    @Mock private ValidationService validationService;

    @BeforeEach
    public void setUp(){
        this.controller = new OrdersController(service, validationService);
    }

    @Test
    void createOrder() {
        OrderRequestDTO requestDTO = new OrderRequestDTO.Builder()
            .setOrderId(FAKE_ORDER_ID)
            .setOrderState(OrderState.PENDING)
            .setProductId(FAKE_PRODUCT_ID)
            .setUserId(FAKE_USER_ID)
            .setQuantity(QUANTITY).build();

        ResponseEntity<OrderRequestDTO> response = new ResponseEntity<>(requestDTO, null, HttpStatus.CREATED);

        when(this.service.createOrder(requestDTO)).thenReturn(response.getBody());

        assertEquals(201, this.controller.createOrder(requestDTO).getStatusCodeValue());
        assertEquals(FAKE_ORDER_ID, this.controller.createOrder(requestDTO).getBody().getOrderId());
    }

    @Test
    void getOrder() {
        OrderResponseDTO responseDTO = new OrderResponseDTO.Builder()
            .setOrderId(FAKE_ORDER_ID)
            .setOrderState(OrderState.PENDING)
            .setProductId(FAKE_PRODUCT_ID)
            .setUserId(FAKE_USER_ID)
            .setQuantity(QUANTITY).build();

        ResponseEntity<OrderResponseDTO> response = new ResponseEntity<>(responseDTO, null, HttpStatus.OK);

        when(this.service.getOrder(FAKE_ORDER_ID)).thenReturn(responseDTO);

        assertEquals(200,this.controller.getOrder(FAKE_ORDER_ID).getStatusCodeValue());
        assertEquals(FAKE_ORDER_ID,this.controller.getOrder(FAKE_ORDER_ID).getBody().getOrderId());
    }

    @Test
    public void shouldThrowNotFoundException(){
        Order order = Order.builder()
            .productId(FAKE_PRODUCT_ID)
            .orderId(FAKE_ORDER_ID)
            .qty(1)
            .orderState(OrderState.CREATED)
            .userId(FAKE_USER_ID)
            .build();
        when(this.service.getOrder(FAKE_ORDER_ID)).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, ()->this.service.getOrder(order.getOrderId()));
    }

    @Test
    void cancelOrder() {
        ResponseEntity<Boolean> response = new ResponseEntity<>(true, null, HttpStatus.OK);

        when(this.service.cancelOrder(FAKE_ORDER_ID)).thenReturn(response.getBody());

        assertEquals(200, this.controller.cancelOrder(FAKE_ORDER_ID).getStatusCodeValue());
        assertEquals(Boolean.TRUE, this.controller.cancelOrder(FAKE_ORDER_ID).getBody());
    }

    @Test
    void approveOrder() {
        ResponseEntity<Boolean> response = new ResponseEntity<>(true, null, HttpStatus.OK);

        when(this.service.approveOrder(FAKE_ORDER_ID)).thenReturn(response.getBody());

        assertEquals(200, this.controller.approveOrder(FAKE_ORDER_ID).getStatusCodeValue());
        assertEquals(Boolean.TRUE, this.controller.approveOrder(FAKE_ORDER_ID).getBody());
    }
}