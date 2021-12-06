package com.angel.models.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class Event {

    private String userId;
    //association property orderId
    private String orderId;

}
