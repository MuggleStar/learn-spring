package com.mugglestar.learn.spring.scs.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author MuggleStar
 * @date 2020/11/21 15:05
 */

@EnableBinding(Source.class)
public class SendService {

    @Autowired
    private Source source;

    public void sendMessage(String msg) {
        try {
            source.output().send(MessageBuilder.withPayload(msg).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
