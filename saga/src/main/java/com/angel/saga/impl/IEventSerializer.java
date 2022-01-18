package com.angel.saga.impl;

import com.angel.models.api.IEvent;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.util.SerializationUtils;


public class IEventSerializer implements Serializer<IEvent> {

    @Override
    public byte[] serialize(String s, IEvent t) {
        return SerializationUtils.serialize(t);
    }
}
