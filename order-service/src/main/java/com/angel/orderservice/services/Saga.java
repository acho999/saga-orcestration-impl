package com.angel.orderservice.services;

import events.Event;
import commands.Command;

public interface Saga {

    void onEvent(Event event);

    void publishEvent(Command command);

}
