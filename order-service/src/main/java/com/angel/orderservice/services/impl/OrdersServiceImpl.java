package com.angel.orderservice.services.impl;

import com.angel.models.DTO.OrderRequestDTO;
import com.angel.models.DTO.OrderResponseDTO;
import com.angel.orderservice.models.Order;
import com.angel.orderservice.repos.OrdersRepo;
import com.angel.orderservice.services.api.OrdersService;
import com.angel.saga.api.Saga;
import com.angel.models.commands.Command;
import com.angel.models.commands.CreateOrderCommand;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.angel.models.states.OrderState;
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

        this.saga.publishCreateOrderCommand(CreateOrderCommand.builder()
                                                .orderId(order.getId())
                                                .productId(order.getProductId())
                                                .quantity(order.getQuantity())
                                                .userId(order.getUserId())
                                                .state(OrderState.ORDER_PENDING)
                                                .build());

        Order newOrder = this.mapper.map(order, Order.class);
        newOrder.setOrderState(OrderState.ORDER_PENDING);
        this.repo.saveAndFlush(newOrder);
        return true;
    }

    @Override//as parameter may be orderId
    public boolean cancelOrder(OrderRequestDTO order) {
        return false;
    }

    @Override//as parameter may be orderId
    public boolean approveOrder(OrderRequestDTO order) {
        return false;
    }


}

