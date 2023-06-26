package com.angel.orderservice.services.impl;

import com.angel.models.DTO.OrderRequestDTO;
import com.angel.models.events.OrderCreatedEvent;
import com.angel.models.states.OrderState;
import com.angel.orderservice.exceptions.NotFoundException;
import com.angel.orderservice.models.Order;
import com.angel.orderservice.repos.OrdersRepo;
import com.angel.saga.api.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.angel.models.constants.CommonConstants.FAKE_ORDER_ID;
import static com.angel.models.constants.CommonConstants.FAKE_PRODUCT_ID;
import static com.angel.models.constants.CommonConstants.FAKE_TOPIC;
import static com.angel.models.constants.CommonConstants.FAKE_USER_ID;
import static com.angel.models.constants.CommonConstants.QUANTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrdersServiceImplTest {

    @InjectMocks
    private OrdersServiceImpl ordersServiceTest;
    @Mock private  OrdersRepo repo;
    @Mock private  SendMessage send;

    @Test
    void shouldGetOrderById() {
        Order order = Order.builder()
            .productId(FAKE_PRODUCT_ID)
            .orderId(FAKE_ORDER_ID)
            .qty(1)
            .orderState(OrderState.CREATED)
            .userId(FAKE_USER_ID)
            .build();

        when(this.repo.findById(FAKE_ORDER_ID)).thenReturn(Optional.of(order));
        assertEquals(FAKE_ORDER_ID,this.ordersServiceTest.getOrder(FAKE_ORDER_ID).getOrderId());
    }

    @Test
    void shouldCreateOrder() {
        Order order = Order.builder()
            .productId(FAKE_PRODUCT_ID)
            .orderId(FAKE_ORDER_ID)
            .qty(1)
            .orderState(OrderState.CREATED)
            .userId(FAKE_USER_ID)
            .build();
        OrderRequestDTO dto = new OrderRequestDTO.Builder()
            .setOrderId(FAKE_ORDER_ID)
            .setOrderState(OrderState.PENDING)
            .setProductId(FAKE_PRODUCT_ID)
            .setUserId(FAKE_USER_ID)
            .setQuantity(QUANTITY).build();

        when(this.repo.saveAndFlush(any(Order.class))).thenReturn(order);
        OrderRequestDTO reqDto = this.ordersServiceTest.createOrder(dto);
        assertEquals(order.getUserId(), reqDto.getUserId());
    }

    @Test
    void shouldCancelOrder() {
        Order order = Order.builder()
            .productId(FAKE_PRODUCT_ID)
            .orderId(FAKE_ORDER_ID)
            .qty(1)
            .orderState(OrderState.CREATED)
            .userId(FAKE_USER_ID)
            .build();
        String id = FAKE_ORDER_ID;
        order.setOrderState(OrderState.CANCELLED);
        when(this.repo.findById(id)).thenReturn(Optional.of(order));
        assertTrue(this.ordersServiceTest.cancelOrder(id));
    }

    @Test
    void shouldApproveOrder() {
        Order order = Order.builder()
            .productId(FAKE_PRODUCT_ID)
            .orderId(FAKE_ORDER_ID)
            .qty(1)
            .orderState(OrderState.CREATED)
            .userId(FAKE_USER_ID)
            .build();
        String id = FAKE_ORDER_ID;
        order.setOrderState(OrderState.PENDING);
        when(this.repo.findById(id)).thenReturn(Optional.of(order));
        assertTrue(this.ordersServiceTest.approveOrder(id));
    }

    @Test
    public void shouldThrowNotFoundException(){
        String id = FAKE_ORDER_ID;
        doThrow(NotFoundException.class).when(this.repo).findById(id);
        assertThrows(NotFoundException.class,()->this.ordersServiceTest.getOrder(id));
    }

    @Test
    void shouldSendMessage(){
        InOrder order = Mockito.inOrder(send);
        send.sendMessage(FAKE_TOPIC, new OrderCreatedEvent());
        order.verify(send, Mockito.calls(1)).sendMessage(ArgumentMatchers.eq(FAKE_TOPIC), any());
    }
}