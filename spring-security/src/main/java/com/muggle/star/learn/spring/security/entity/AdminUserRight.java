package com.muggle.star.learn.spring.security.entity;

/**
 * 后台用户权限
 *
 * @author lujianrong
 * @since 2020/12/7 10:43
 */
public class AdminUserRight {

    private Long id;
    private Long adminUserId;
    private Long roleId;
    private Long roleGroupId;
    private Long menuId;
    private String menuUrl;
    private Boolean getRight;
    private Boolean postRight;
    private Boolean putRight;
    private Boolean deleteRight;

    public AdminUserRight(Boolean getRight, Boolean postRight, Boolean putRight, Boolean deleteRight) {
        this.getRight = getRight;
        this.postRight = postRight;
        this.putRight = putRight;
        this.deleteRight = deleteRight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleGroupId() {
        return roleGroupId;
    }

    public void setRoleGroupId(Long roleGroupId) {
        this.roleGroupId = roleGroupId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Boolean getGetRight() {
        return getRight;
    }

    public void setGetRight(Boolean getRight) {
        this.getRight = getRight;
    }

    public Boolean getPostRight() {
        return postRight;
    }

    public void setPostRight(Boolean postRight) {
        this.postRight = postRight;
    }

    public Boolean getPutRight() {
        return putRight;
    }

    public void setPutRight(Boolean putRight) {
        this.putRight = putRight;
    }

    public Boolean getDeleteRight() {
        return deleteRight;
    }

    public void setDeleteRight(Boolean deleteRight) {
        this.deleteRight = deleteRight;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdminUserRight{");
        sb.append("id=").append(id);
        sb.append(", adminUserId=").append(adminUserId);
        sb.append(", roleId=").append(roleId);
        sb.append(", roleGroupId=").append(roleGroupId);
        sb.append(", menuId=").append(menuId);
        sb.append(", menuUrl='").append(menuUrl).append('\'');
        sb.append(", getRight=").append(getRight);
        sb.append(", postRight=").append(postRight);
        sb.append(", putRight=").append(putRight);
        sb.append(", deleteRight=").append(deleteRight);
        sb.append('}');
        return sb.toString();
    }
}
