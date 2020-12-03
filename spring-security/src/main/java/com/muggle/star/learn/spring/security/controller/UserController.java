package com.muggle.star.learn.spring.security.controller;

import com.muggle.star.learn.spring.security.entity.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lujianrong
 * @since 2020/12/2 19:12
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/login")
    public String login(@RequestBody User user){
        System.out.println("success");
        return "success";
    }



}
