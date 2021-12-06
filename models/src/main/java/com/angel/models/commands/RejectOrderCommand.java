package com.angel.models.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RejectOrderCommand extends Command{

    private String reason;

    @Builder
    public RejectOrderCommand(String reason, String userId, String orderId){
        super(userId, orderId);
        this.reason = reason;
    }

}
