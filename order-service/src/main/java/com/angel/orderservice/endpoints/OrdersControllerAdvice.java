package com.angel.orderservice.endpoints;

import com.angel.orderservice.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class OrdersControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<NotFoundException> notFoundExceptionHandler(HttpServletRequest request,
                                                                           NotFoundException ex) {//here parameters are autowired
        return new ResponseEntity(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<IllegalArgumentException> illegalArgumentExceptionHandler(HttpServletRequest request,
                                                                             IllegalArgumentException ex) {//here parameters are autowired
        return new ResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

}
