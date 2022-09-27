package com.angel.saga.impl;

import com.angel.models.api.IEvent;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.util.SerializationUtils;

import java.util.Objects;


public class IEventSerializer implements Serializer<IEvent> {

    @Override
    public byte[] serialize(String s, IEvent t) {
        if(s.isEmpty() || Objects.isNull(s)){
            throw new IllegalArgumentException("The topicName can not be null or empty string!");
        }
        if(Objects.isNull(t)){
            throw new IllegalArgumentException("The event/command can not be null!");
        }
        return SerializationUtils.serialize(t);
    }
}
