package com.muggle.star.learn.spring.security.entity;

/**
 * 后台用户
 *
 * @author lujianrong
 * @since 2020/12/3 10:28
 */
public class AdminUser {

    private Long id;
    private String email;
    private String userName;
    private String password;

    public AdminUser(Long id, String email, String userName, String password) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdminUser{");
        sb.append("id=").append(id);
        sb.append(", email='").append(email).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
