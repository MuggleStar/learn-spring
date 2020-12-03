package com.muggle.star.learn.spring.security.controller;

import com.muggle.star.learn.spring.security.entity.User;
import com.muggle.star.learn.spring.security.utils.JwtTokenUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lujianrong
 * @since 2020/12/2 19:12
 */
@RestController
public class UserController {


    @Resource
    private AuthenticationManagerBuilder authenticationManagerBuilder;


    @GetMapping("/hello")
    public String index(){
        return "hello world";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        // 登陆验证
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //创建jwt信息
        String token1 = JwtTokenUtils.createToken(user.getUserName(),"admin", true);
        return token1;
    }

    @GetMapping("/role")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String roleInfo(){

        return "role";
    }

    @GetMapping("/role2")
    @PreAuthorize("hasAnyAuthority('admin2')")
    public String rolekdream(){
        return "role2";
    }

}
