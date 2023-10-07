package com.angel.orderservice.saga.impl;

import com.angel.models.commands.ApproveOrderCommand;
import com.angel.models.commands.RejectOrderCommandPayment;
import com.angel.models.commands.RejectOrderCommandProduct;
import com.angel.models.events.Event;
import com.angel.orderservice.saga.api.SagaListener;
import com.angel.orderservice.services.api.ValidationService;
import com.angel.saga.api.Factory;
import com.angel.saga.api.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.angel.models.constants.CommonConstants.COMMAND_CAN_NOT_BE_NULL;
import static com.angel.models.constants.TopicConstants.APPROVE_ORDER_COMMAND;
import static com.angel.models.constants.TopicConstants.GROUP_ID;
import static com.angel.models.constants.TopicConstants.ORDER_APPROVED_EVENT;
import static com.angel.models.constants.TopicConstants.ORDER_REJECTED_EVENT;
import static com.angel.models.constants.TopicConstants.REJECT_ORDER_COMMAND_PAYMENT;
import static com.angel.models.constants.TopicConstants.REJECT_ORDER_COMMAND_PRODUCT;

@KafkaListener(topics = { APPROVE_ORDER_COMMAND, REJECT_ORDER_COMMAND_PAYMENT,
                          REJECT_ORDER_COMMAND_PRODUCT}, groupId = GROUP_ID)
@Component
public class OrderSagaListenerImpl implements SagaListener {

    private final ValidationService validationService;
    private final SendMessage sendService;
    private final Factory factory;

    @Autowired
    public OrderSagaListenerImpl(SendMessage sendService, Factory factory, ValidationService validationService) {
        this.sendService = sendService;
        this.factory = factory;
        this.validationService = validationService;
    }

    @Override//7
    @KafkaHandler
    public Event handleApproveOrderCommand(ApproveOrderCommand command){
        this.validationService.validateIsNotNull(command, COMMAND_CAN_NOT_BE_NULL);
        Event event = this.factory.readCommand(APPROVE_ORDER_COMMAND,
                                               ORDER_APPROVED_EVENT,
                                               command);
        this.sendService.sendMessage(ORDER_APPROVED_EVENT, event);
        return event;
    }

    @Override//15
    @KafkaHandler
    public Event handleRejectOrderCommandProduct(RejectOrderCommandProduct command){
        this.validationService.validateIsNotNull(command, COMMAND_CAN_NOT_BE_NULL);
        Event event = this.factory.readCommand(REJECT_ORDER_COMMAND_PRODUCT,
                                               ORDER_REJECTED_EVENT,
                                               command);
        this.sendService.sendMessage(ORDER_REJECTED_EVENT, event);
        return event;
    }

    @Override//13
    @KafkaHandler
    public Event handleRejectOrderCommandPayment(RejectOrderCommandPayment command){
        this.validationService.validateIsNotNull(command, COMMAND_CAN_NOT_BE_NULL);
        Event event = this.factory.readCommand(REJECT_ORDER_COMMAND_PAYMENT,
                                               ORDER_REJECTED_EVENT,
                                               command);
        this.sendService.sendMessage(ORDER_REJECTED_EVENT, event);
        return event;
    }

}
