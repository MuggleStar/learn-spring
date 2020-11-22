package com.mugglestar.learn.spring.scs.controller;

import com.mugglestar.learn.spring.scs.stream.SendService;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lujianrong
 * @since 2020/11/20 17:19
 */
@RestController
public class MessageSendController {

    @Resource
    SendService producer;

    @RequestMapping("/msg/{key}")
    public String sendMsg(@PathVariable String key, @RequestBody Object msg){
        Map<String,Object> payload = new HashMap<>();
        payload.put(key,msg);
        //将负载送入通道
        producer.messageOutput().send(MessageBuilder.withPayload(payload).build());
        return "sent";
    }

    @RequestMapping("/error/{key}")
    public String sendErr(@PathVariable String key, @RequestBody Object err){
        Map<String,Object> payload = new HashMap<>();
        payload.put(key,err);
        producer.errorOutput().send(MessageBuilder.withPayload(payload).build());
        return "sent";
    }

}
