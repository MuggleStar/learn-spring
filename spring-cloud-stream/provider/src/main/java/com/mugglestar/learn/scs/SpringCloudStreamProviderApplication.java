package com.mugglestar.learn.scs;

import com.mugglestar.learn.scs.stream.SendService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author lujianrong
 * @since 2020/11/20 17:16
 */
@EnableBinding(value = {SendService.class})
@SpringBootApplication
public class SpringCloudStreamProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamProviderApplication.class, args);
    }

}
