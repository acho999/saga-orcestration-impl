package com.angel.models.events;

import com.angel.models.api.IEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class Event implements IEvent {

    private String userId;
    //association property orderId
    private String orderId;

}
