package com.angel.models.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class Command {

    private String userId;
    //association property orderId
    private String orderId;

}
