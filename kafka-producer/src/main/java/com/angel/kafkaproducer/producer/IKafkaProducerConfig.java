package com.angel.kafkaproducer.producer;

import com.angel.models.commands.Command;
import com.angel.models.events.Event;

import java.util.Properties;

public interface IKafkaProducerConfig {

    Command commandFactory(Event event, String topic);
    Properties getConsumerProperties();
    Event eventFactory(Command command, String topic);
    Properties getProducerProperties();
    void sendEvent(String nextTopic, Event event);
    void sendCommand(String nextTopic, Command command);
    void sendTest();


}
