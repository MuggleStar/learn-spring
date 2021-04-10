package learn.spring.security.filter;

import learn.spring.security.entity.BackendUser;
import learn.spring.security.service.UserService;
import learn.spring.security.utils.JwtTokenHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Madison
 * @since 2020/12/3 16:36
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private UserService userService = new UserService();

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String tokenHeader = request.getHeader(TOKEN_HEADER);
        //如果请求头中没有Authorization信息则直接放行了
        if (tokenHeader == null || !tokenHeader.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        //如果请求头中有token,则进行解析，并且设置认证信息
        if (!JwtTokenHelper.isExpiration(tokenHeader.replace(TOKEN_PREFIX, ""))) {
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
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {

        String token = tokenHeader.replace(TOKEN_PREFIX, "");
        String username = JwtTokenHelper.getUserName(token);
        if (username == null) {
            return null;
        }

        // 获得权限 添加到权限上去
        BackendUser userByUsername = userService.getUserByUsername(username);
        String role = userByUsername.getRole();
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add((GrantedAuthority) () -> role);

        return new UsernamePasswordAuthenticationToken(username, null, roles);

    }

}
