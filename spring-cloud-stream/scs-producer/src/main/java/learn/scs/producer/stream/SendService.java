package learn.scs.producer.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author Madison
 * @date 2020/11/21 15:05
 */

public interface SendService {

    String USER_REGISTER ="user_register";
    String USER_LOGIN ="user_login";

    /**
     * 通过Output注解定义输出通道
     * @return
     */
    @Output(SendService.USER_REGISTER)
    MessageChannel messageOutput();

    @Output(SendService.USER_LOGIN)
    MessageChannel userLogin();

}
