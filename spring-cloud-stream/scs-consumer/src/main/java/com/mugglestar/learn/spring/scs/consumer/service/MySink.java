package com.mugglestar.learn.spring.scs.consumer.service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author MuggleStar
 * @since 2020/11/23 15:09
 */
public interface MySink {

    @Input("user_register")
    SubscribableChannel userRegister();

    @Input("user_login")
    SubscribableChannel userLogin();

}
