package com.angel.orderservice.kafka;

import com.angel.orderservice.models.Order;

public class KafkaProducerConfigImpl implements IKafkaProducerConfig{


    @Override
    public boolean processOrder(final Order order){

        return true;
    }
}
