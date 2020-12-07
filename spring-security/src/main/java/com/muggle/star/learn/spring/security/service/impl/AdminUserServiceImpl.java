package com.muggle.star.learn.spring.security.service.impl;

import com.muggle.star.learn.spring.security.entity.AdminUser;
import com.muggle.star.learn.spring.security.entity.AdminUserRight;
import com.muggle.star.learn.spring.security.service.AdminUserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 后台用户
 *
 * @author lujianrong
 * @since 2020/12/3 19:05
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    /**
     * 根据用户名搜索
     *
     * @param userName
     * @return
     */
    @Override
    public AdminUser selectByUserName(String userName) {

        String defaultPassword = "$2a$10$vuFxav4ZXSOCq96XwRrOJOEfF3rRBZZxbxu04MlwqOTe4lvipoJ7K";

        Map<String ,AdminUser> userMap = new HashMap<>(16);
        userMap.put("KangKang",new AdminUser(1L,"KangKang@star.com","KangKang",defaultPassword));
        userMap.put("Michael",new AdminUser(2L,"Michael@star.com","Michael",defaultPassword));
        userMap.put("Maria",new AdminUser(3L,"Maria@star.com","Maria",defaultPassword));
        userMap.put("Jane",new AdminUser(4L,"Jane@star.com","Jane",defaultPassword));

        return userMap.get(userName);
    }

    /**
     * 根据用户id获取用户权限
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, AdminUserRight> getAdminUserRightByUserId(Long userId) {

        String menuUrl1 = "/menu/one";
        String menuUrl2 = "/menu/two";
        String menuUrl3 = "/menu/three";
        AdminUserRight getRight = new AdminUserRight(true,false,false,false);
        AdminUserRight allRight = new AdminUserRight(true,true,true,true);

        Map<String, AdminUserRight> michaelRightMap = new HashMap<>();
        michaelRightMap.put(menuUrl1,allRight);
        michaelRightMap.put(menuUrl2,getRight);

        Map<String, AdminUserRight> mariaRightMap = new HashMap<>();
        mariaRightMap.put(menuUrl2,allRight);
        mariaRightMap.put(menuUrl3,getRight);

        Map<Long, Map<String, AdminUserRight>> allRightMap = new HashMap<>();

        allRightMap.put(2L,michaelRightMap);
        allRightMap.put(3L,mariaRightMap);

        return allRightMap.get(userId);
    }

}
