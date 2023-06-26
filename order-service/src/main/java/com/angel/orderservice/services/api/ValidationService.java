package com.angel.orderservice.services.api;

public interface ValidationService {

    void validateIsNotNull(Object obj, String message) throws IllegalArgumentException;

    void isNotEmptyString(String input, String message) throws IllegalArgumentException;
}
