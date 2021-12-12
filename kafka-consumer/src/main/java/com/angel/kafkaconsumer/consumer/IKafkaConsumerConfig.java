package com.angel.kafkaconsumer.consumer;

import com.angel.models.commands.Command;
import com.angel.models.events.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.Properties;

public interface IKafkaConsumerConfig {

    Command readEvent(String topic, String nextTopic, Event event, ConsumerRecord<String, String> record) throws JsonProcessingException;

    Event readCommand(String currentTopic, String nextTopic, Command command, ConsumerRecord<String, String> record)
        throws JsonProcessingException;

    void consumeTest();

    Properties getConsumerProperties();

}
