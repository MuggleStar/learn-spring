package com.muggle.star.learn.spring.security.util;


import com.muggle.star.learn.spring.security.SecurityApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 加密测试
 *
 * @author lujianrong
 * @since 2020/12/3
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SecurityApplication.class)
public class EncryptUtilTest {

    @Resource
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void encode(){

        System.out.println(bCryptPasswordEncoder.encode("password"));
        System.out.println(bCryptPasswordEncoder.encode("123456"));
        System.out.println(bCryptPasswordEncoder.encode("123456"));

    }
    
    @Test
    public void matches(){

        String password = "$2a$10$ECUu8xcWub3P4aOPpuxvKuMRoqvpYmX8uJIrvwYB2ilYz07ATz5BS";
        String wrongPassword = "$2a$10$7i3Q.gXN43mR9Ub6x43HhewPqDfvgoRhTk2xeKsixEm3pOkhHQH4C";

        System.out.println(bCryptPasswordEncoder.matches("123456",password));
        System.out.println(bCryptPasswordEncoder.matches("123456",wrongPassword));

    }

}