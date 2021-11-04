package com.angel.orderservice.services;

import DTO.OrderRequestDTO;

import java.util.Collection;

public interface OrdersService {
    Collection<OrderRequestDTO> getAll();
    OrderRequestDTO getOrder(String id);
    boolean createOrder(OrderRequestDTO order);
}
