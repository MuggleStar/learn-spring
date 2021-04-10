package learn.scs.consumer.service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Madison
 * @since 2020/11/23 15:09
 */
public interface MySink {

    @Input("user_register")
    SubscribableChannel userRegister();

    @Input("user_login")
    SubscribableChannel userLogin();

}
