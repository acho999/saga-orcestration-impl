package com.angel.orderservice.services.api;

import DTO.OrderRequestDTO;
import DTO.OrderResponseDTO;

import java.util.Collection;

public interface OrdersService {
    Collection<OrderRequestDTO> getAll();
    OrderResponseDTO getOrder(String id);
    boolean createOrder(OrderRequestDTO order);
}
