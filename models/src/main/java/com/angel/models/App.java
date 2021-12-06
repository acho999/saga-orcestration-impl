package com.angel.models;

import com.angel.models.events.Event;
import com.angel.models.events.OrderCreatedEvent;
import com.angel.models.states.OrderState;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public static void main(String[] args) {

        Event ev = OrderCreatedEvent.builder().orderId("123").productId("123").quantity(1).state(
            OrderState.ORDER_PENDING).userId("123").build();

        System.out.println(ev.getClass().getSimpleName());


    }
}
