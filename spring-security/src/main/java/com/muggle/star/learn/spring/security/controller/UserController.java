package com.muggle.star.learn.spring.security.controller;

import com.muggle.star.learn.spring.security.entity.BackendUser;
import com.muggle.star.learn.spring.security.service.UserService;
import com.muggle.star.learn.spring.security.utils.JwtTokenHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 后台用户
 *
 * @author MuggleStar
 * @since 2020/12/2 19:12
 */
@RestController
public class UserController {

    @Resource
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Resource
    private UserService userService;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public String login(@RequestBody BackendUser user){

        // 登陆验证
        BackendUser userByUsername = userService.getUserByUsername(user.getUserName());
        boolean matches = bCryptPasswordEncoder.matches(user.getPassword(), userByUsername.getPassword());
        if (matches) {
            return JwtTokenHelper.createToken(userByUsername.getUserName(),true);
        } else {
            return "fail";
        }
    }

    @GetMapping("/hello")
    public String index(){
        return "hello world";
    }

    @PostMapping("/insert")
    @PreAuthorize("hasAnyAuthority('insert')")
    public String insert(){
        return "insert";
    }
    @PostMapping("/delete")
    @PreAuthorize("hasAnyAuthority('delete')")
    public String delete(){
        return "delete";
    }
    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('update')")
    public String update(){
        return "update";
    }

    @PostMapping("/select")
    public String select(){
        return "select";
    }

    @PostMapping("/admin")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String roleInfo(){
        return "admin";
    }



}
