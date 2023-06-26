package com.angel.orderservice.services.impl;

import com.angel.orderservice.services.api.ValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.angel.models.constants.CommonConstants.ARGUMENT_NOT_EMPTY_STRING;
import static com.angel.models.constants.CommonConstants.ARGUMENT_NOT_NULL;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class ValidationServiceImplTest {

    @Mock private ValidationService validationServiceTest;

    @Test
    void validateIsNotNull() {
        doThrow(IllegalArgumentException.class).when(validationServiceTest)
            .validateIsNotNull(null, ARGUMENT_NOT_NULL);
        assertThrows(IllegalArgumentException.class, ()->this.validationServiceTest
            .validateIsNotNull(null, ARGUMENT_NOT_NULL), ARGUMENT_NOT_NULL);
    }

    @Test
    void isNotEmptyString() {
        doThrow(IllegalArgumentException.class).when(validationServiceTest)
            .isNotEmptyString("", ARGUMENT_NOT_EMPTY_STRING);
        assertThrows(IllegalArgumentException.class,()->this.validationServiceTest
                .isNotEmptyString("", ARGUMENT_NOT_EMPTY_STRING)
        );
    }
}