package com.angel.models.commands;

import com.angel.models.api.IEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Command implements IEvent {

    private String userId;
    //association property orderId
    private String orderId;

}
