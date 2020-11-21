package com.mugglestar.learn.spring.scs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author lujianrong
 * @since 2020/11/20 17:19
 */
@Controller
public class MessageSendController {

    @Autowired
    @Output(Source.OUTPUT)
    private MessageChannel channel;

    @RequestMapping("/send")
    public void write(String msg) {
        channel.send(MessageBuilder.withPayload(msg).build());
    }
}
