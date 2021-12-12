package com.angel.orderservice.services.impl;

import com.angel.models.DTO.OrderRequestDTO;
import com.angel.models.DTO.OrderResponseDTO;
import com.angel.models.commands.Command;
import com.angel.orderservice.models.Order;
import com.angel.orderservice.repos.OrdersRepo;
import com.angel.orderservice.services.api.OrdersService;
import com.angel.orderservice.startClass.StartClass;
import com.angel.saga.api.SagaOrchestration;
import com.angel.models.commands.CreateOrderCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.angel.models.states.OrderState;

import java.util.concurrent.Semaphore;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepo repo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private StartClass start;

    private Thread thread = null;

    @Autowired
    private SagaOrchestration sagaOrchestration;

    private Semaphore mutex = new Semaphore(1);

    @Override
    public OrderResponseDTO getOrder(String id){
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderResponseDTO dto = this.mapper.map(this.repo.getById(id),OrderResponseDTO.class);

        return  dto;
    }

    @Override
    public OrderRequestDTO createOrder(OrderRequestDTO order)
        throws InterruptedException {
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Order newOrder = this.mapper.map(order, Order.class);
        newOrder.setState(OrderState.ORDER_PENDING);
        this.repo.saveAndFlush(newOrder);

        OrderRequestDTO dto = order;
        dto.setId(newOrder.getOrderId());
        dto.setOrderState(newOrder.getState());

        CreateOrderCommand cmd = CreateOrderCommand.builder()
            .orderId(newOrder.getOrderId())
            .productId(order.getProductId())
            .quantity(order.getQuantity())
            .userId(order.getUserId())
            .state(OrderState.ORDER_PENDING)
            .build();

        this.mutex.acquire();

           Runnable run = new Runnable() {
               @Override
               public void run() {

                       try {
                           start.runAll(cmd);
                       } catch (JsonProcessingException e) {
                           e.printStackTrace();
                       }

               }
           };
           if(thread == null){
               thread = new Thread(run);
               thread.start();
           }
        this.mutex.release();

        //this.sagaOrchestration.testProducer();
        return dto;
    }

    @Override//as parameter may be orderId
    public boolean cancelOrder(Command command) {

        Order order = this.repo.getById(command.getUserId());

        order.setState(OrderState.ORDER_CANCELLED);

        this.repo.saveAndFlush(order);

        return false;
    }

    @Override//as parameter may be orderId
    public boolean approveOrder(Command command) {

        Order order = this.repo.getById(command.getUserId());

        order.setState(OrderState.ORDER_CREATED);

        this.repo.saveAndFlush(order);

        return true;
    }


}

