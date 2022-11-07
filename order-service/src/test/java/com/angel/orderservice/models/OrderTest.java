package com.angel.orderservice.models;

import com.angel.models.states.OrderState;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class OrderTest {

    @Test
    public void orderTest(){
        Order order = Mockito.spy(Order.builder().build());//here we test builder and allargsconstructor
        order.setOrderId("fakeOrderId");
        order.setQty(1);
        order.setProductId("fakeProductId");
        order.setOrderState(OrderState.CREATED);
        order.setUserId("fakeUserId");

        Mockito.verify(order).setOrderId("fakeOrderId");//with verify we test the actual setOrderId invocation
        Mockito.verify(order).setQty(1);
        Mockito.verify(order).setProductId("fakeProductId");
        Mockito.verify(order).setOrderState(OrderState.CREATED);
        Mockito.verify(order).setUserId("fakeUserId");

        assertEquals("fakeOrderId", order.getOrderId());
        assertEquals("fakeProductId", order.getProductId());
        assertEquals("fakeUserId", order.getUserId());
        assertEquals(1, order.getQty());
        assertEquals(OrderState.CREATED, order.getOrderState());

        Order anotherOrder = new Order();//here we test default constructor
        anotherOrder.setOrderId("fakeOrderId");
        anotherOrder.setQty(1);
        anotherOrder.setProductId("fakeProductId");
        anotherOrder.setOrderState(OrderState.CREATED);
        anotherOrder.setUserId("fakeUserId");

        assertEquals("fakeOrderId", anotherOrder.getOrderId());
        assertEquals("fakeProductId", anotherOrder.getProductId());
        assertEquals("fakeUserId", anotherOrder.getUserId());
        assertEquals(1, anotherOrder.getQty());
        assertEquals(OrderState.CREATED, anotherOrder.getOrderState());
    }

}