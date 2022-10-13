package com.angel.orderservice.services.impl;

import com.angel.models.DTO.OrderRequestDTO;
import com.angel.models.states.OrderState;
import com.angel.orderservice.exceptions.NotFoundException;
import com.angel.orderservice.models.Order;
import com.angel.orderservice.repos.OrdersRepo;
import com.angel.saga.api.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrdersServiceImplTest {

    @InjectMocks
    private OrdersServiceImpl ordersServiceTest;
    @Mock private  OrdersRepo repo;
    @Mock private  SendMessage send;
    private Order order;

    @BeforeEach
    void setUp() {
        this.order = Order.builder()
            .productId("shoes")
            .orderId("fakeId")
            .quantity(1)
            .state(OrderState.CREATED)
            .userId("userId")
            .build();
    }

    @Test
    void shouldGetOrderById() {
        String id = "fakeId";
        when(this.repo.findById(id)).thenReturn(Optional.of(this.order));
        assertEquals(id,this.ordersServiceTest.getOrder(id).getOrderId());

        this.order.setOrderId(null);
        IllegalArgumentException idNull = assertThrows(
            IllegalArgumentException.class,() -> this.ordersServiceTest.getOrder(order.getOrderId())
        );
        assertEquals("Id can not be null or empty string!", idNull.getMessage());

        this.order.setOrderId("");
        IllegalArgumentException idEmptyString = assertThrows(
            IllegalArgumentException.class,()->this.ordersServiceTest.getOrder(order.getOrderId())
        );
        assertEquals("Id can not be null or empty string!", idEmptyString.getMessage());

        this.order.setOrderId(id);
        when(this.repo.findById(id)).thenReturn(Optional.empty());
        NotFoundException notFoundException = assertThrows(
            NotFoundException.class, ()->this.ordersServiceTest.getOrder(order.getOrderId()));
        assertEquals("Order not found!", notFoundException.getMessage());
    }

    @Test
    void shouldCreateOrder() {
        OrderRequestDTO dto = new OrderRequestDTO.Builder()
            .setOrderId(this.order.getOrderId())
            .setOrderState(this.order.getState())
            .setProductId(this.order.getProductId())
            .setQuantity(this.order.getQuantity())
            .setUserId(this.order.getUserId())
            .build();

        when(this.repo.saveAndFlush(any(Order.class))).thenReturn(this.order);
        OrderRequestDTO reqDto = this.ordersServiceTest.createOrder(dto);
        assertEquals(this.order.getUserId(), reqDto.getUserId());

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class, ()->{this.ordersServiceTest.createOrder(null);}
        );
        assertEquals("Order can not be null!", ex.getMessage());
    }

    @Test
    void shouldCancelOrder() {
        String id = "fakeId";
        this.order.setState(OrderState.CANCELLED);
        when(this.repo.findById(id)).thenReturn(Optional.of(this.order));
        assertTrue(this.ordersServiceTest.cancelOrder(id));

        this.order.setState(OrderState.CREATED);
        when(this.repo.findById(id)).thenReturn(Optional.of(this.order));
        assertTrue(this.ordersServiceTest.cancelOrder(id));

        this.order.setOrderId(null);
        IllegalArgumentException idNull = assertThrows(
            IllegalArgumentException.class,() -> this.ordersServiceTest.cancelOrder(this.order.getOrderId())
        );
        assertEquals("Id can not be null or empty string!", idNull.getMessage());

        this.order.setOrderId("");
        IllegalArgumentException idEmptyString = assertThrows(
            IllegalArgumentException.class,()->this.ordersServiceTest.cancelOrder(this.order.getOrderId())
        );
        assertEquals("Id can not be null or empty string!", idEmptyString.getMessage());

        this.order.setOrderId(id);
        when(this.repo.findById(id)).thenReturn(Optional.empty());
        NotFoundException notFoundException = assertThrows(
            NotFoundException.class, ()->this.ordersServiceTest.cancelOrder(this.order.getOrderId())
        );
        assertEquals("Order not found!", notFoundException.getMessage());
    }

    @Test
    void shouldApproveOrder() {
        String id = "fakeId";
        this.order.setState(OrderState.PENDING);
        when(this.repo.findById(id)).thenReturn(Optional.of(this.order));
        assertTrue(this.ordersServiceTest.approveOrder(id));

        this.order.setOrderId(null);
        IllegalArgumentException idNull = assertThrows(
            IllegalArgumentException.class,() -> this.ordersServiceTest.approveOrder(this.order.getOrderId())
        );
        assertEquals("Id can not be null or empty string!", idNull.getMessage());

        this.order.setOrderId("");
        IllegalArgumentException idEmptyString = assertThrows(
            IllegalArgumentException.class,()->this.ordersServiceTest.approveOrder(this.order.getOrderId())
        );
        assertEquals("Id can not be null or empty string!", idEmptyString.getMessage());

        this.order.setOrderId(id);
        when(this.repo.findById(id)).thenReturn(Optional.empty());
        NotFoundException notFoundException = assertThrows(
            NotFoundException.class, ()->this.ordersServiceTest.approveOrder(this.order.getOrderId())
        );
        assertEquals("Order not found!", notFoundException.getMessage());
    }
}