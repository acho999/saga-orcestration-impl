package com.angel.orderservice.services.api;

import com.angel.models.DTO.OrderRequestDTO;
import com.angel.models.DTO.OrderResponseDTO;
import com.angel.models.commands.Command;

import java.util.Collection;

public interface OrdersService {

    OrderResponseDTO getOrder(String id);

    boolean createOrder(OrderRequestDTO order);

    boolean cancelOrder(Command cmd);

    boolean approveOrder(Command cmd);
}
