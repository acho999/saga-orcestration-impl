package com.angel.orderservice.services.api;

import com.angel.models.DTO.OrderRequestDTO;
import com.angel.models.DTO.OrderResponseDTO;
import com.angel.models.commands.Command;
import com.fasterxml.jackson.core.JsonProcessingException;


public interface OrdersService {

    OrderResponseDTO getOrder(String id);

    OrderRequestDTO createOrder(OrderRequestDTO order)
        throws JsonProcessingException, InterruptedException;

    boolean cancelOrder(Command cmd);

    boolean approveOrder(Command cmd);
}
