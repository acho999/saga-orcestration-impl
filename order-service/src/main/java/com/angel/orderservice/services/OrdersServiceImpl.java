package com.angel.orderservice.services;

import com.angel.orderservice.models.Order;
import com.angel.orderservice.repos.OrdersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepo repo;

    @Override
    public Collection<Order> getAll() {
        return null;
    }
}
