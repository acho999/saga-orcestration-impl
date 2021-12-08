package com.angel.kafkaconsumer.consumer;

import com.angel.models.commands.Command;
import com.angel.models.events.Event;

public interface IKafkaConsumerConfig {

    Command readEvent(String topic, String nextTopic, Event event);
    Event readCommand(String currentTopic, String nextTopic, Command command);

}
