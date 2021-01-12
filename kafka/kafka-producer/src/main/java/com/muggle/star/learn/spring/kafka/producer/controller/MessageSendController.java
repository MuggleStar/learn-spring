package com.muggle.star.learn.spring.kafka.producer.controller;

import com.muggle.star.learn.spring.kafka.producer.kafka.KafkaProducer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Madison
 * @since 2020/11/20 17:19
 */
@Controller
@RestController
public class MessageSendController {

    @Resource
    private KafkaProducer kafkaProducer;

    @GetMapping("/sendMessage")
    public String sendMessage(String message) {
        kafkaProducer.send(KafkaProducer.TOPIC_TEST,message);
        return "成功";
    }
}
