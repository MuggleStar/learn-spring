package com.muggle.star.learn.spring.kafka.producer.controller;

import com.muggle.star.learn.spring.kafka.producer.kafka.KafkaProducer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author lujianrong
 * @since 2020/11/20 17:19
 */
@Controller
public class MessageSendController {

    @Resource
    private KafkaProducer kafkaProducer;

    @RequestMapping("/sendMessage")
    public String sendMessage(String message) {
        kafkaProducer.send(message);
        return "sucess";
    }
}
