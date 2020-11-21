package com.mugglestar.learn.scs.controller;

import com.mugglestar.learn.scs.stream.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
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
    SendService sendService;


    @RequestMapping("sendMessage")
    public void sendMessage(String message){

        sendService.message().send(MessageBuilder.withPayload(message).build());
    }

}
