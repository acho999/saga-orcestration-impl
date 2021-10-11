package com.angel.orderservice.services;

import com.angel.orderservice.models.Order;

import java.util.Collection;

public interface OrdersService {

    Collection<Order> getAll();

}
