package com.angel.orderservice.services;

import com.angel.orderservice.models.Order;

import java.util.Collection;
import java.util.List;

public interface OrdersService {

    List<Order> getAll();
    List<Order> getOrder(String id);
}
