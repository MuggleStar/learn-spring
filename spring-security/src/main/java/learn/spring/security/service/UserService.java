package learn.spring.security.service;

import learn.spring.security.entity.BackendUser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟用户数据库查询
 *
 * @author Madison
 * @since 2020/12/3 19:05
 */
@Service
public class UserService {

    /**
     * 用户查询
     *
     * @param userName
     * @return
     */
    public BackendUser getUserByUsername(String userName) {

        // 123456 加密后的密码
        String encryptedPassword = "$2a$10$vuFxav4ZXSOCq96XwRrOJOEfF3rRBZZxbxu04MlwqOTe4lvipoJ7K";
        
        Map<String,BackendUser> userMap = new HashMap<>(16);
        userMap.put("admin",new BackendUser("admin",encryptedPassword,"admin"));
        userMap.put("KangKang",new BackendUser("KangKang",encryptedPassword,"insert"));
        userMap.put("Michael",new BackendUser("Michael","","delete"));
        userMap.put("Mary",new BackendUser("Mary",encryptedPassword,"update"));
        userMap.put("Jane",new BackendUser("Jane",encryptedPassword,"select"));
        
        return userMap.get(userName);
    }
}
