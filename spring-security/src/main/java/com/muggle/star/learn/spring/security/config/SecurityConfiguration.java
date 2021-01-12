package com.muggle.star.learn.spring.security.config;

import com.muggle.star.learn.spring.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Spring-Security 配置
 *
 * @author Madison
 * @since 2020/12/3 10:22
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Resource
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/hello").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll()
                // 开启验证
                .anyRequest().authenticated()
                // 禁用session
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 使用自己定义的拦截机制，拦截jwt
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                //授权错误信息处理
                .exceptionHandling()
                //用户访问资源没有携带正确的token
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                //用户访问没有授权资源
                .accessDeniedHandler(jwtAccessDeniedHandler);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //使用的密码比较方式
        return new BCryptPasswordEncoder();
    }

}
