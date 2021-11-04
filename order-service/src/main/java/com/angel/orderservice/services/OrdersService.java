package com.angel.orderservice.services;

import DTO.OrderDTO;
import com.angel.orderservice.models.Order;

import java.util.Collection;
import java.util.List;

public interface OrdersService {
    Collection<OrderDTO> getAll();
    OrderDTO getOrder(String id);
    boolean createOrder(OrderDTO order);
}
