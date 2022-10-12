package com.angel.orderservice.services.impl;

import com.angel.models.DTO.OrderRequestDTO;
import com.angel.models.DTO.OrderResponseDTO;
import com.angel.models.events.OrderCreatedEvent;
import com.angel.models.states.OrderState;
import com.angel.orderservice.exceptions.NotFoundException;
import com.angel.orderservice.models.Order;
import com.angel.orderservice.repos.OrdersRepo;
import com.angel.orderservice.services.api.OrdersService;
import com.angel.saga.api.SendMessage;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static com.angel.models.constants.TopicConstants.ORDER_CREATED_EVENT;

@Service
public class OrdersServiceImpl implements OrdersService {


    private final OrdersRepo repo;
    private final ModelMapper mapper;
    private final SendMessage send;

    @Autowired
    public OrdersServiceImpl(OrdersRepo repo, ModelMapper mapper, SendMessage send) {
        this.repo = repo;
        this.mapper = mapper;
        this.send = send;
    }

    /**
     * 
     * @param id
     * @return
     */
    @Override
    public OrderResponseDTO getOrder(String id) {
        if (Objects.isNull(id) || id.isEmpty()) {
            throw new IllegalArgumentException("Id can not be null or empty string!");
        }
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Optional<Order> order = this.repo.findById(id);
        if (order.isEmpty()) {
            throw new NotFoundException("Order not found!");
        }
        OrderResponseDTO dto = this.mapper.map(order.get(), OrderResponseDTO.class);
        return dto;
    }

    @Override
    @Transactional
    public OrderRequestDTO createOrder(OrderRequestDTO order){

        if (Objects.isNull(order)) {
            throw new IllegalArgumentException("Order can not be null!");
        }

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
            .build();

        send.sendMessage(ORDER_CREATED_EVENT, event);
        return dto;
    }

    @Override
    @Transactional
    public boolean cancelOrder(String orderId) {

        if (Objects.isNull(orderId) || orderId.isEmpty()) {
            throw new IllegalArgumentException("Id can not be null or empty string!");
        }
        Optional<Order> order = this.repo.findById(orderId);

        if (order.isEmpty()) {
            throw new NotFoundException("Order not found!");
        }

        if (order.get().getState().equals(OrderState.CANCELLED)) {
            return true;
        }

        order.get().setState(OrderState.CANCELLED);
        this.repo.saveAndFlush(order.get());
        return true;
    }

    @Override
    @Transactional
    public boolean approveOrder(String orderId) {

        if (Objects.isNull(orderId) || orderId.isEmpty()) {
            throw new IllegalArgumentException("Id can not be null or empty string!");
        }

        Optional<Order> order = this.repo.findById(orderId);

        if (order.isEmpty()) {
            throw new NotFoundException("Order not found!");
        }

        order.get().setState(OrderState.CREATED);
        this.repo.saveAndFlush(order.get());

        return true;
    }


}

