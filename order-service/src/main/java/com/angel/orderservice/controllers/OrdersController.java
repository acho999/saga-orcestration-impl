package com.angel.orderservice.controllers;

import com.angel.models.DTO.OrderRequestDTO;
import com.angel.models.DTO.OrderResponseDTO;
import com.angel.orderservice.services.api.OrdersService;
import com.angel.saga.api.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
public class OrdersController {

    @Autowired
    private OrdersService service;

    @Autowired
    private Saga saga;

    @RequestMapping(method = RequestMethod.POST, value = "/create",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createOrder(@RequestBody OrderRequestDTO request){

        this.service.createOrder(request);

        return new ResponseEntity<>(HttpStatus.CREATED).ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{orderId}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderRequestDTO> getOrder(@PathVariable String id) {

        OrderResponseDTO response = this.service.getOrder(id);

        return new ResponseEntity<>(response, HttpStatus.OK).ok().build();
    }

}
