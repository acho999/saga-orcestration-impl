package com.angel.orderservice.models;

import com.angel.models.states.OrderState;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

import static com.angel.models.constants.CommonConstants.FAKE_ORDER_ID;
import static com.angel.models.constants.CommonConstants.FAKE_USER_ID;
import static org.junit.jupiter.api.Assertions.*;
import static com.angel.models.constants.CommonConstants.FAKE_PRODUCT_ID;

@ActiveProfiles("test")
class OrderTest {

    @Test
    public void orderTest(){
        Order order = Mockito.spy(
            Order.builder().orderId(FAKE_ORDER_ID).qty(1).productId(FAKE_PRODUCT_ID)
                .orderState(OrderState.CREATED).userId(FAKE_USER_ID)
                .build());//here we test builder and allargsconstructor

//        Mockito.verify(order).setOrderId("fakeOrderId");//with verify we test the actual setOrderId invocation
//        Mockito.verify(order).setQty(1);
//        Mockito.verify(order).setProductId("fakeProductId");
//        Mockito.verify(order).setOrderState(OrderState.CREATED);
//        Mockito.verify(order).setUserId("fakeUserId");

        assertEquals(FAKE_ORDER_ID, order.getOrderId());
        assertEquals(FAKE_PRODUCT_ID, order.getProductId());
        assertEquals(FAKE_USER_ID, order.getUserId());
        assertEquals(1, order.getQty());
        assertEquals(OrderState.CREATED, order.getOrderState());

        Order anotherOrder = new Order();//here we test default constructor

        assertTrue(Objects.nonNull(anotherOrder));
    }

}