package com.angel.kafkaproducer.producer;

import com.angel.models.commands.Command;
import com.angel.models.events.Event;


public interface IKafkaProducerConfig {

    void sendEvent(String nextTopic, Event event);
    void sendCommand(String nextTopic, Command command);


}
