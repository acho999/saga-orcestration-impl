package com.angel.orderservice.endpoints;

import com.angel.models.DTO.OrderRequestDTO;
import com.angel.models.DTO.OrderResponseDTO;
import com.angel.orderservice.services.api.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("Api for working with orders.")
@RestController
@RequestMapping(value = "/orders")
public class OrdersController {

    private final OrdersService service;

    @Autowired
    public OrdersController(OrdersService service) {
        this.service = service;
    }

    @ApiOperation("Creates order.")
    @RequestMapping(method = RequestMethod.POST, value = "/create",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderRequestDTO> createOrder(@RequestBody OrderRequestDTO request){

        OrderRequestDTO dto = this.service.createOrder(request);

        return new ResponseEntity<>(dto,null,HttpStatus.CREATED);
    }

    @ApiOperation("Gets order by id.")
    @RequestMapping(method = RequestMethod.GET, value = "/getOrder/{orderId}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable String orderId) {

        OrderResponseDTO response = this.service.getOrder(orderId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation("Responsible for changing order state to CANCELED.")
    @RequestMapping(method = RequestMethod.POST, value = "/cancel/{orderId}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> cancelOrder(@PathVariable String orderId) {

        boolean isCanceled = this.service.cancelOrder(orderId);

        return ResponseEntity.ok(isCanceled);
    }

    @ApiOperation("Responsible for changing order state to APPROVED.")
    @RequestMapping(method = RequestMethod.POST, value = "/approve/{orderId}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> approveOrder(@PathVariable String orderId) {

        boolean isApproved = this.service.approveOrder(orderId);

        return ResponseEntity.ok(isApproved);
    }



}
