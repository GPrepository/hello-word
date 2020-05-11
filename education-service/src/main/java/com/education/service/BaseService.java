package com.education.service;

import com.education.common.model.AdminUserSession;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.mapper.BaseMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
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
    protected static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    /**
     * 添加或修改
     * @param updateFlag
     * @param modelBeanMap
     * @return
     */
    public Result saveOrUpdate(boolean updateFlag, Map modelBeanMap) {
        try {
            String message = "";
            if (updateFlag) {
                this.checkParams(modelBeanMap);
                this.update(modelBeanMap);
                message = "修改";
            } else {
                modelBeanMap.put("create_date", new Date());
                this.save(modelBeanMap);
                message = "添加";
            }
            return Result.success(ResultCode.SUCCESS, message + "成功"); //new ResultCode(ResultCode.SUCCESS, message + "成功");
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return Result.success(ResultCode.FAIL, "操作异常");
    }

    /**
     * 移除时间字段
     * @param params
     */
    protected void checkParams(Map params) {
        if (params != null) {
            params.remove("create_date");
            params.remove("last_login_time");
        }
    }

    /**
     * 添加数据
     * @param modelMap
     * @return
     */
    public int save(Map modelMap) {
        return mapper.save(modelMap);
    }

    /**
     * 更新数据
     * @param modelMap
     * @return
     */
    public int update(Map modelMap) {
        return mapper.update(modelMap);
    }

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

    public Result deleteById(Map modelBeanMap) {
        return deleteById((Integer) modelBeanMap.get("id"));
    }

    public Result deleteById(Integer id) {
        int result = mapper.deleteById(id);
        if (result > 0) {
            return Result.success(ResultCode.SUCCESS, "删除成功");
        }
        return Result.fail(ResultCode.FAIL, "删除数据异常");
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
