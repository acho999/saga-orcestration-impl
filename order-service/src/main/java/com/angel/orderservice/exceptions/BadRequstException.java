package com.angel.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BadRequstException extends RuntimeException{

    public BadRequstException(String message) {
        super(message);
    }

    public BadRequstException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequstException(Throwable cause) {
        super(cause);
    }

    public BadRequstException(String message, Throwable cause, boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
