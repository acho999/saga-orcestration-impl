package com.angel.orderservice.services;

import DTO.OrderRequestDTO;
import com.angel.orderservice.kafka.KafkaProducerConfig;
import com.angel.orderservice.models.Order;
import com.angel.orderservice.repos.OrdersRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import states.OrderState;

import java.util.Collection;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepo repo;

    @Autowired
    private KafkaProducerConfig producer;

    @Autowired
    private ModelMapper mapper;

    public OrdersServiceImpl(){
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    @Transactional
    public Collection<OrderRequestDTO> getAll() {

        return null;
    }

    @Override
    @Transactional
    public OrderRequestDTO getOrder(String id){

        return  null;
    }

    @Override
    @Transactional
    public boolean createOrder(OrderRequestDTO order) {
        Order newOrder = this.mapper.map(order,Order.class);
        newOrder.setOrderState(OrderState.ORDER_PENDING);
        this.repo.saveAndFlush(newOrder);
        if (this.producer.processOrder(newOrder)){
            return true;
        }
        return false;
    }


}

