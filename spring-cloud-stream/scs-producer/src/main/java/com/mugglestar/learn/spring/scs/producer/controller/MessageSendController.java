package com.mugglestar.learn.spring.scs.producer.controller;

import com.mugglestar.learn.spring.scs.producer.stream.SendService;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author lujianrong
 * @since 2020/11/20 17:19
 */
@RestController
public class MessageSendController {

    @Resource
    SendService producer;

    @RequestMapping("/sendMessage")
    public String sendMsg(String message){
        //将负载送入通道
        producer.messageOutput().send(MessageBuilder.withPayload(message).build());
        return "success";
    }

    @RequestMapping("/userLogin")
    public String sendErr(String message){
        producer.userLogin().send(MessageBuilder.withPayload(message).build());
        return "success";
    }

}