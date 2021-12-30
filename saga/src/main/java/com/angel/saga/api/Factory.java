package com.angel.saga.api;

import com.angel.models.api.IEvent;
import com.angel.models.commands.Command;
import com.angel.models.events.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface Factory {

    Event eventFactory(Command cmd, String topic);

    Command commandFactory(Event evt, String topic);

    Command readEvent(String currentTopic, String nextTopicCommand, Event evt, String message)
        throws JsonProcessingException;

    Event readCommand(String currentTopic, String nextTopicCommand, Command cmd, String message)
        throws JsonProcessingException;

    JsonNode convertJsonToJsonNode(String message)
        throws JsonProcessingException;


}
