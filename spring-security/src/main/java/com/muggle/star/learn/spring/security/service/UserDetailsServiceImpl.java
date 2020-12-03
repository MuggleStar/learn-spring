package com.muggle.star.learn.spring.security.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author lujianrong
 * @since 2020/12/3 19:05
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        //直接写死数据信息，可以在这里获取数据库的信息并进行验证

        UserDetails userDetails  = User.withUsername(userName).password(new BCryptPasswordEncoder().encode("123456"))
                .authorities("admin").build();
        return userDetails;
    }
}
