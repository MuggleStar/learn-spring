package com.muggle.star.learn.spring.security.filter;

import com.muggle.star.learn.spring.security.utils.JwtTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lujianrong
 * @since 2020/12/3 16:36
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        //如果请求头中没有Authorization信息则直接放行了
        if(tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtils.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }
        //如果请求头中有token,则进行解析，并且设置认证信息
        if(!JwtTokenUtils.isExpiration(tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX,""))){
            //设置上下文
            UsernamePasswordAuthenticationToken authentication = getAuthentication(tokenHeader);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        super.doFilterInternal(request, response, chain);
    }

    /**
     * 获取用户信息
     *
     * @param tokenHeader
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader){
        String token = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        String username = JwtTokenUtils.getUserName(token);
        // 获得权限 添加到权限上去
        String role = JwtTokenUtils.getUserRole(token);
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role;
            }
        });
        if(username != null){
            return new UsernamePasswordAuthenticationToken(username, null,roles);
        }
        return null;
    }

}
