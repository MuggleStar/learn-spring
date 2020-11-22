package com.muggle.star.learn.spring.kafka.producer.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

/**
 * @author MuggleStar
 * @date 2020/11/21 17:54
 */
@Service
public class KafkaProducer {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 自定义topic
     */
    public static final String TOPIC_TEST = "test_topic_02";


    public void send(String topic,String message) {
        //发送消息
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                logger.error("失败",throwable);
            }
            @Override
            public void onSuccess(SendResult<String, String> stringObjectSendResult) {
                logger.info("成功");
            }
        });
    }
}
