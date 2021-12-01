package com.angel.orderservice.services.impl;

import com.angel.kafkautils.producer.IKafkaProducerConfig;
import com.angel.orderservice.services.api.Saga;
import events.Event;
import commands.Command;
import org.springframework.beans.factory.annotation.Autowired;

public class SagaImpl implements Saga {

    @Autowired
    private IKafkaProducerConfig producer;

    @Override
    public void onEvent(Event event) {

    }

    @Override
    public void publishEvent(Command command) {

    }
}
