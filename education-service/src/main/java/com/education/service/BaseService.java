package com.education.service;

import com.education.common.model.AdminUserSession;
import com.education.common.utils.ObjectUtils;
import com.education.mapper.BaseMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * service 父类
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/26 21:15
 */
public abstract class BaseService<M extends BaseMapper> {

    @Autowired
    protected M mapper;

    public Map getAdminUser() {
        AdminUserSession adminSession = getAdminUserSession();
        if (ObjectUtils.isNotEmpty(adminSession)) {
            return adminSession.getUserMap();
        }
        return null;
    }

    public Integer getAdminUserId() {
        if (ObjectUtils.isNotEmpty(getAdminUser())) {
            return (Integer) getAdminUser().get("id");
        }
        return null;
    }

    /**
     * 获取管理员用户信息
     * @return
     */
    public AdminUserSession getAdminUserSession() {
        Subject subject = SecurityUtils.getSubject();
        AdminUserSession userSession = (AdminUserSession)subject.getPrincipal();
        if (ObjectUtils.isNotEmpty(userSession)) {
            return userSession;
        }
        return null;
    }
}
