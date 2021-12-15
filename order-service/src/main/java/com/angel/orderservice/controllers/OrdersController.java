package com.angel.orderservice.controllers;

import com.angel.models.DTO.OrderRequestDTO;
import com.angel.models.DTO.OrderResponseDTO;
import com.angel.orderservice.services.api.OrdersService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @RequestMapping(method = RequestMethod.POST, value = "/create",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderRequestDTO> createOrder(@RequestBody OrderRequestDTO request)
        throws JsonProcessingException, InterruptedException {

        OrderRequestDTO dto = this.service.createOrder(request);

        return new ResponseEntity<>(dto,null,HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getOrder/{orderId}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable String orderId) {

        OrderResponseDTO response = this.service.getOrder(orderId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
