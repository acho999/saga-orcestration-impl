package com.angel.saga.api;

import com.angel.models.api.IEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface SendMessage {

    void sendMessage(String topicName, IEvent event);

}
