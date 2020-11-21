package com.mugglestar.learn.scs.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lujianrong
 * @since 2020/11/20 15:40
 */
public interface SendService {

        @Output("test-binding")
        MessageChannel message();


}
