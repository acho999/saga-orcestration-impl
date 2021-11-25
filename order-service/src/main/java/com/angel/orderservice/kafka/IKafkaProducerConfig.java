package com.angel.orderservice.kafka;

import com.angel.orderservice.models.Order;

public interface IKafkaProducerConfig {

    public boolean processOrder(final Order order);

}
