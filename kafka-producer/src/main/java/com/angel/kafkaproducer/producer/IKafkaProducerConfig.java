package com.angel.kafkaproducer.producer;

import com.angel.models.commands.Command;
import com.angel.models.events.Event;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Properties;

public interface IKafkaProducerConfig {

    Properties getConsumerProperties();
    Properties getProducerProperties();
    void sendEvent(String nextTopic, Event event) throws JsonProcessingException;
    void sendCommand(String nextTopic, Command command) throws JsonProcessingException;
    void sendTest();


}
