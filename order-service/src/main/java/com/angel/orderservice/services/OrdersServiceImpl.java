package com.angel.orderservice.services;

import com.angel.orderservice.models.Order;
import com.angel.orderservice.repos.OrdersRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepo repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public Flux<Order> getAll() {
        return null;
    }

    @Override
    @Transactional
    public Mono<Order> getOrder(String id){
        return  null;
    }
}
