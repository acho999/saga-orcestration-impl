package com.angel.orderservice.models;

import com.angel.models.states.OrderState;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class OrderTest {

    @Test
    public void orderUnitTest(){
        Order order = Mockito.spy(Order.builder().build());//here we test builder and allargsconstructor
        order.setOrderId("fakeOrderId");
        order.setQuantity(1);
        order.setProductId("fakeProductId");
        order.setState(OrderState.CREATED);
        order.setUserId("fakeUserId");

        Mockito.verify(order).setOrderId("fakeOrderId");//with verify we test the actual setOrderId invocation
        Mockito.verify(order).setQuantity(1);
        Mockito.verify(order).setProductId("fakeProductId");
        Mockito.verify(order).setState(OrderState.CREATED);
        Mockito.verify(order).setUserId("fakeUserId");

        assertEquals("fakeOrderId", order.getOrderId());
        assertEquals("fakeProductId", order.getProductId());
        assertEquals("fakeUserId", order.getUserId());
        assertEquals(1, order.getQuantity());
        assertEquals(OrderState.CREATED, order.getState());

        Order anotherOrder = new Order();//here we test default constructor
        anotherOrder.setOrderId("fakeOrderId");
        anotherOrder.setQuantity(1);
        anotherOrder.setProductId("fakeProductId");
        anotherOrder.setState(OrderState.CREATED);
        anotherOrder.setUserId("fakeUserId");

        assertEquals("fakeOrderId", anotherOrder.getOrderId());
        assertEquals("fakeProductId", anotherOrder.getProductId());
        assertEquals("fakeUserId", anotherOrder.getUserId());
        assertEquals(1, anotherOrder.getQuantity());
        assertEquals(OrderState.CREATED, anotherOrder.getState());
    }

}