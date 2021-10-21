package com.angel.orderservice.controllers;

import com.angel.orderservice.models.Order;
import com.angel.orderservice.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
public class OrdersController {

    @Autowired
    private OrdersService service;

    @RequestMapping(method = RequestMethod.GET, value = "/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable String id) {
        return null;
    }

}
