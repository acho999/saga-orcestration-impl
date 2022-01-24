package com.angel.orderservice.saga.api;

import com.angel.models.commands.ApproveOrderCommand;
import com.angel.models.commands.RejectOrderCommandPayment;
import com.angel.models.commands.RejectOrderCommandProduct;
import com.angel.models.events.Event;

public interface SagaListener {

    Event handleApproveOrderCommand(ApproveOrderCommand command);
    Event handleRejectOrderCommandProduct(RejectOrderCommandProduct command);
    Event handleRejectOrderCommandPayment(RejectOrderCommandPayment message);

}
