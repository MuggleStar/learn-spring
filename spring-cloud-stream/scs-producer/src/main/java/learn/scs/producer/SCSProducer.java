package learn.scs.producer;

import learn.scs.producer.stream.SendService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author Madison
 * @since 2020/11/20 17:16
 */
@EnableBinding({SendService.class})
@SpringBootApplication
public class SCSProducer {

    public static void main(String[] args) {
        SpringApplication.run(SCSProducer.class, args);
    }

}
