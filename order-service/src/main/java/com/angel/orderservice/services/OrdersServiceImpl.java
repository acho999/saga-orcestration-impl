package com.angel.orderservice.services;

import DTO.OrderDTO;
import com.angel.orderservice.models.Order;
import com.angel.orderservice.repos.OrdersRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
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

    public OrdersServiceImpl(){
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    @Transactional
    public Collection<OrderDTO> getAll() {

        return null;
    }

    @Override
    @Transactional
    public OrderDTO getOrder(String id){

        return  null;
    }

    @Override
    @Transactional
    public boolean createOrder(OrderDTO order) {
        Order newOrder = this.mapper.map(order,Order.class);
        this.repo.saveAndFlush(newOrder);
        if (this.processOrder(newOrder)){
            return true;
        }
        return false;
    }

    private boolean processOrder(final Order order){



        return true;
    }
}

