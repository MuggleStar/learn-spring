package learn.scs.consumer.service;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

/**
 * @author Madison
 * @since 2020/11/23 14:34
 */
@Service
public class ReceiveService {

    @StreamListener("user_register")
    public void receiveInput1(String receiveMsg) {
        System.out.println("input1 receive: " + receiveMsg);
    }

    @StreamListener("user_login")
    public void receiveInput2(String receiveMsg) {
        System.out.println("input2 receive: " + receiveMsg);
    }

}