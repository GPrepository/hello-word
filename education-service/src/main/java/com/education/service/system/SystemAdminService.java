package com.education.service.system;

import com.education.common.exception.BusinessException;
import com.education.common.model.AdminUserSession;
import com.education.common.utils.MapTreeUtils;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.mapper.system.SystemAdminMapper;
import com.education.service.BaseService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 管理员service
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/26 21:15
 */
@Service
public class SystemAdminService extends BaseService<SystemAdminMapper> {

    private static final Logger logger = LoggerFactory.getLogger(SystemAdminService.class);
    @Autowired
    private SystemMenuService systemMenuService;
    @Autowired
    private SystemAdminRoleService systemAdminRoleService;
    @Autowired
    private SystemRoleMenuService systemRoleMenuService;


    /**
     * 管理员登录
     * @param userName
     * @param password
     * @return
     */
    public Result login(String userName, String password) {
        Result result = new Result(ResultCode.SUCCESS, "登录成功");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, password);
        try {
            subject.login(usernamePasswordToken); // 执行用户登录
        } catch (Exception e) {
            result.setCode(ResultCode.FAIL);
            if (e instanceof UnknownAccountException) {
                result.setMessage("用户不存在");
            } else {
                result.setMessage("用户名或密码错误");
            }

            Throwable throwable = e.getCause();
            if (ObjectUtils.isNotEmpty(throwable)) {
                if (throwable instanceof BusinessException) {
                    result.setMessage(throwable.getMessage());
                }
            }
            logger.error("登录失败", e);
        }
        return result;
    }

    /**
     * 获取用户菜单及其权限
     * @param adminUserSession
     */
    public void loadUserMenuAndPermission(AdminUserSession adminUserSession) {
        List<Map> menuList = null;
        // 如果是超级管理员，加载所有菜单及其权限
        if (adminUserSession.isSuperAdmin()) {
            menuList = systemMenuService.queryList(null);
        } else {
            Integer adminId = (Integer) adminUserSession.getUserMap().get("id");
            // 先查询用户角色
            List<Map> roleList = systemAdminRoleService.findRoleListByAdminId(adminId);
            if (ObjectUtils.isNotEmpty(roleList)) {
                Set<Integer> roleIds = new HashSet<>();
                roleList.forEach(roleInfo -> {
                    roleIds.add((Integer) roleInfo.get("role_id"));
                });
                adminUserSession.setRoleIds(roleIds);
                Map params = new HashMap<>();
                params.put("roleIds", roleIds);
                menuList = systemRoleMenuService.getMenuListByRoleIds(params);
            }
        }

        menuList.forEach(menu -> {
            String permission = (String) menu.get("permission"); // 用户权限标识
            adminUserSession.addPermission(permission);
        });

        List<Map> treeMenuList = MapTreeUtils.buildTreeData(menuList);
        adminUserSession.setMenuList(treeMenuList);
    }

    public int update(Map params) {
        return mapper.update(params);
    }
}
