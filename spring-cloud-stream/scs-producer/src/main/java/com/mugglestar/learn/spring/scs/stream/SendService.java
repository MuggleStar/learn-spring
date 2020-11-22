package com.mugglestar.learn.spring.scs.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author MuggleStar
 * @date 2020/11/21 15:05
 */

public interface SendService {

    String OUTPUT_CHANNEL ="test_topic_01";
    String ERROR_CHANNEL ="test_topic_01";

    /**
     * 通过Output注解定义输出通道
     * @return
     */
    @Output(SendService.OUTPUT_CHANNEL)
    MessageChannel messageOutput();

    @Output(SendService.ERROR_CHANNEL)
    MessageChannel errorOutput();

}
