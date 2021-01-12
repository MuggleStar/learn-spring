package com.muggle.star.learn.spring.kafka.consumer.kafka;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Madison
 * @date 2020/11/21 18:11
 */
@Component
public class KafkaConsumer {


    Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 自定义topic
     */
    private static final String TOPIC_TEST = "test_topic_01";

    @KafkaListener(topics = TOPIC_TEST,groupId = "default")
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            logger.info("----------------- record =" + record);
            logger.info("------------------ message =" + message);
        }
    }

}
