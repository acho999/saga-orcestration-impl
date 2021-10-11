package com.angel.orderservice.services;

import com.angel.orderservice.models.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface OrdersService {

    Flux<Order> getAll();
    Mono<Order> getOrder(String id);
}
