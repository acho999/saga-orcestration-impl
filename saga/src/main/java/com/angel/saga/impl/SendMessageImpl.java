package com.angel.saga.impl;

import com.angel.models.api.IEvent;
import com.angel.saga.api.SendMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class SendMessageImpl implements SendMessage {


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public synchronized void sendMessage(String topicName, IEvent event, ObjectMapper mapper) throws JsonProcessingException {

        String msg = mapper.writeValueAsString(event);

        this.kafkaTemplate.send(topicName, msg);

//        ListenableFuture<SendResult<String, String>> future =
//            this.kafkaTemplate.send(topicName, msg);
//
//        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
//
//            @Override
//            public void onSuccess(SendResult<String, String> result) {
//                System.out.println("Sent message=[" + msg +
//                                   "] with offset=[" + result.getRecordMetadata().offset() + "]");
//            }
//            @Override
//            public void onFailure(Throwable ex) {
//                System.out.println("Unable to send message=["
//                                   + msg + "] due to : " + ex.getMessage());
//            }
//        });
    }

}
