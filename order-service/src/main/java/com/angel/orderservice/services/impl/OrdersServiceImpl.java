package com.angel.orderservice.services.impl;

import DTO.OrderRequestDTO;
import DTO.OrderResponseDTO;
import com.angel.orderservice.models.Order;
import com.angel.orderservice.repos.OrdersRepo;
import com.angel.orderservice.services.api.OrdersService;
import com.angel.orderservice.services.api.Saga;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import states.OrderState;

import java.util.Collection;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepo repo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Saga saga;

    public OrdersServiceImpl(){
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public Collection<OrderRequestDTO> getAll() {

        return null;
    }

    @Override
    public OrderResponseDTO getOrder(String id){

        return  null;
    }

    @Override
    public boolean createOrder(OrderRequestDTO order) {
        Order newOrder = this.mapper.map(order,Order.class);
        newOrder.setOrderState(OrderState.ORDER_PENDING);
        this.repo.saveAndFlush(newOrder);
        return false;
    }


}

