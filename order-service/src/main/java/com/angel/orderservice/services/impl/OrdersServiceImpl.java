package com.angel.orderservice.services.impl;

import com.angel.models.DTO.OrderRequestDTO;
import com.angel.models.DTO.OrderResponseDTO;
import com.angel.models.events.OrderCreatedEvent;
import com.angel.orderservice.models.Order;
import com.angel.orderservice.repos.OrdersRepo;
import com.angel.orderservice.services.api.OrdersService;
import com.angel.saga.api.SendMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.angel.models.states.OrderState;
import static com.angel.models.constants.TopicConstants.*;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepo repo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private SendMessage send;

    @Override
    public OrderResponseDTO getOrder(String id) {
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Order order = this.repo.findById(id).get();
        OrderResponseDTO dto = this.mapper.map(order, OrderResponseDTO.class);
        return dto;
    }

    @Override
    @Transactional
    public OrderRequestDTO createOrder(OrderRequestDTO order)
        throws JsonProcessingException {
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Order newOrder = this.mapper.map(order, Order.class);
        newOrder.setState(OrderState.PENDING);
        this.repo.saveAndFlush(newOrder);

        OrderRequestDTO dto = order;
        dto.setId(newOrder.getOrderId());
        dto.setOrderState(newOrder.getState());

        OrderCreatedEvent event = OrderCreatedEvent.builder()
            .orderId(newOrder.getOrderId())
            .productId(dto.getProductId())
            .quantity(dto.getQuantity())
            .userId(dto.getUserId())
            .state(OrderState.PENDING)
            .price(dto.getPrice())
            .build();

        send.sendMessage(ORDER_CREATED_EVENT, event);
        return dto;
    }

    @Override
    @Transactional
    public boolean cancelOrder(String orderId) {

        Order order = this.repo.findById(orderId).get();

        if (order.getState().equals(OrderState.CANCELLED)) {
            return true;
        }

        order.setState(OrderState.CANCELLED);
        this.repo.saveAndFlush(order);
        return true;
    }

    @Override
    @Transactional
    public boolean approveOrder(String orderId) {

        Order order = this.repo.findById(orderId).get();
        order.setState(OrderState.CREATED);
        this.repo.saveAndFlush(order);

        return true;
    }


}

