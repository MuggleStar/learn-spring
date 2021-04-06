package com.muggle.star.learn.spring.kafka.consumera.kafka;


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
    private static final String TOPIC_TEST = "test_topic_02";

    @KafkaListener(topics = TOPIC_TEST,groupId = "group_a")
    public void listen(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            String topic = record.topic();
            int partition = record.partition();
            long offset = record.offset();
            logger.info(topic + ":" + partition + ":" + offset + ":" + message);
        }
    }

}
