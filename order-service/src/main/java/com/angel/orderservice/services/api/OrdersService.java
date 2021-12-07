package com.angel.orderservice.services.api;

import com.angel.models.DTO.OrderRequestDTO;
import com.angel.models.DTO.OrderResponseDTO;

import java.util.Collection;

public interface OrdersService {

    OrderResponseDTO getOrder(String id);

    boolean createOrder(OrderRequestDTO order);

    boolean cancelOrder(OrderRequestDTO order);

    boolean approveOrder(OrderRequestDTO order);
}
