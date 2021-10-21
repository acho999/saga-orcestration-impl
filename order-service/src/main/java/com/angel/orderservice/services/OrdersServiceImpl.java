package com.angel.orderservice.services;

import com.angel.orderservice.models.Order;
import com.angel.orderservice.repos.OrdersRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepo repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public List<Order> getAll() {
        return null;
    }

    @Override
    @Transactional
    public List<Order> getOrder(String id){
        return  null;
    }
}
