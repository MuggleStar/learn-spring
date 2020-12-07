package com.muggle.star.learn.spring.security.service;

import com.muggle.star.learn.spring.security.entity.AdminUser;
import com.muggle.star.learn.spring.security.entity.AdminUserRight;

import java.util.Map;

/**
 * 后台用户
 *
 * @author lujianrong
 * @since 2020/12/7
 */
public interface AdminUserService {

    /**
     * 根据用户名搜索
     *
     * @param userName
     * @return
     */
    AdminUser selectByUserName(String userName);

    /**
     * 根据用户id获取用户权限
     *
     * @param userId
     * @return
     */
    Map<String, AdminUserRight> getAdminUserRightByUserId(Long userId);

}
