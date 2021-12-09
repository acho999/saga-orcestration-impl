package com.angel.kafkaconsumer.consumer;


import com.angel.models.commands.Command;
import com.angel.models.events.Event;

import java.util.Objects;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface ReadCommand <V extends String, T extends String, C extends Command, E extends Event>{

    Event readCommand(V currentTopic, T nextTopic, C command);

    //Event apply(V var1, T var2, Command var3);

    default BiConsumer<V, E> andThen(BiConsumer<V, E> after) {
        Objects.requireNonNull(after);
        return (V, Event)->{
           after.accept(V,Event);
        };
    }
}
