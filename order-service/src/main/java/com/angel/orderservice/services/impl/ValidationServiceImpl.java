package com.angel.orderservice.services.impl;

import com.angel.orderservice.services.api.ValidationService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public void validateIsNotNull(Object obj, String message) {
        if (Objects.isNull(obj)) {
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public void isNotEmptyString(String input, String message){
        if (input.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
}
