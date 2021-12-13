package com.angel.models.events;

import com.angel.models.api.IEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Event implements IEvent {

    @JsonProperty
    private String userId;
    //association property orderId
    @JsonProperty
    private String orderId;

}
