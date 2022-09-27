package com.angel.saga.impl;

import com.angel.models.api.IEvent;
import com.angel.saga.api.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SendMessageImpl implements SendMessage {


    @Autowired
    private KafkaTemplate<String, IEvent> kafkaTemplate;

    @Override
    public void sendMessage(String topicName, IEvent event){
        if( topicName.isEmpty() || Objects.isNull(topicName)){
            throw new IllegalArgumentException("The topicName can not be null or empty string!");
        }
        if(Objects.isNull(event)){
            throw new IllegalArgumentException("The event can not be null!");
        }
        this.kafkaTemplate.send(topicName, event);

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
