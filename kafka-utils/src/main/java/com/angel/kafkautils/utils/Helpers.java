package com.angel.kafkautils.utils;

import com.angel.models.commands.Command;
import com.angel.models.events.Event;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;

public interface Helpers {

    Command commandFactory(Event event, String topic);
    Properties getConsumerProperties();
    Event eventFactory(Command command, String topic);
    Properties getProducerProperties();
    void produceCommand(String topic, String nextTopic, Event event);
    void produceEvent(String currentTopic, String nextTopic, Command command);


}
