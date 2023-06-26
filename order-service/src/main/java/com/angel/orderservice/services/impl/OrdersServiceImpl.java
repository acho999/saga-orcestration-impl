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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.angel.models.constants.CommonConstants.NOT_FOUND;
import static com.angel.models.constants.TopicConstants.ORDER_CREATED_EVENT;


@Service
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepo repo;
    private final SendMessage send;

    @Autowired
    public OrdersServiceImpl(OrdersRepo repo, SendMessage send) {
        this.repo = repo;
        this.send = send;
    }

    /**
     * 
     * @param id
     * @return
     */
    @Override
    public OrderResponseDTO getOrder(String id) {
        Optional<Order> orderOptional = this.repo.findById(id);
        if (orderOptional.isEmpty()) {
            throw new NotFoundException(NOT_FOUND);
        }
        Order order = orderOptional.get();
        OrderResponseDTO dto = new OrderResponseDTO.Builder()
            .setOrderId(order.getOrderId())
            .setQuantity(order.getQty())
            .setProductId(order.getProductId())
            .setUserId(order.getUserId())
            .setOrderState(order.getOrderState())
            .build();
        return dto;
    }

    @Override
    @Transactional
    public OrderRequestDTO createOrder(OrderRequestDTO order){

        Order newOrder = Order.builder()
            .userId(order.getUserId())
            .qty(order.getQuantity())
            .productId(order.getProductId())
            .orderState(OrderState.PENDING)
            .build();
        this.repo.saveAndFlush(newOrder);

        OrderRequestDTO dto = order;
        dto.setOrderId(newOrder.getOrderId());
        dto.setOrderState(newOrder.getOrderState());

        OrderCreatedEvent event = OrderCreatedEvent.builder()
            .orderId(newOrder.getOrderId())
            .productId(dto.getProductId())
            .quantity(dto.getQuantity())
            .userId(dto.getUserId())
            .state(newOrder.getOrderState())
            .build();

        send.sendMessage(ORDER_CREATED_EVENT, event);
        return dto;
    }

    @Override
    @Transactional
    public boolean cancelOrder(String orderId) {
        Optional<Order> order = this.repo.findById(orderId);

        if (order.isEmpty()) {
            throw new NotFoundException(NOT_FOUND);
        }

        if (order.get().getOrderState().equals(OrderState.CANCELLED)) {
            return true;
        }

        order.get().setOrderState(OrderState.CANCELLED);
        this.repo.saveAndFlush(order.get());
        return order.get().getOrderState().equals(OrderState.CANCELLED);
    }

    @Override
    @Transactional
    public boolean approveOrder(String orderId) {

        Optional<Order> order = this.repo.findById(orderId);

        if (order.isEmpty()) {
            throw new NotFoundException(NOT_FOUND);
        }

        order.get().setOrderState(OrderState.CREATED);
        this.repo.saveAndFlush(order.get());

        return order.get().getOrderState().equals(OrderState.CREATED);
    }
}

