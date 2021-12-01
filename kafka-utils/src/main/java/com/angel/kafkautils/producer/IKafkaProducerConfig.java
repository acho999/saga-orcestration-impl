package com.angel.kafkautils.producer;

public interface IKafkaProducerConfig<T> {
    boolean processOrder(final T order);
}
