package com.mugglestar.learn.spring.scs.consumer;

import com.mugglestar.learn.spring.scs.consumer.service.MySink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

/**
 * @author Madison
 * @since 2020/11/23 14:33
 */
@EnableBinding({MySink.class})
@SpringBootApplication
public class SpringCloudStreamConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamConsumerApplication.class, args);
    }


    @Bean
    public ConsumerCustomRunner customRunner() {
        return new ConsumerCustomRunner();
    }

    public static class ConsumerCustomRunner implements CommandLineRunner {

        @Autowired
        private MySink mySink;

        @Override
        public void run(String... args) throws InterruptedException {
//            while (true) {
//                mySink.userLogin().subscribe(c ->{
//
//                });
//                Thread.sleep(2_00);
//            }
        }

    }


}
