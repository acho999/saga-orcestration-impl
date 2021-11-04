package com.angel.orderservice.services;

import DTO.OrderDTO;
import com.angel.orderservice.models.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface OrdersService {

    Collection<OrderDTO> getAll();
    OrderDTO getOrder(String id);
}
