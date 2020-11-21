package com.mugglestar.learn.spring.scs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author lujianrong
 * @since 2020/11/20 17:16
 */
@SpringBootApplication
public class SpringCloudStreamProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamProviderApplication.class, args);
    }

}
