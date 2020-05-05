package com.education.admin.api.shiro;

import com.education.common.exception.BusinessException;
import com.education.common.model.AdminUserSession;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.ResultCode;
import com.education.common.utils.SpringBeanManager;
import com.education.mapper.system.SystemAdminMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用户身份认证器
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/22 10:58
 */
@Component
public class SystemRealm extends AuthorizingRealm {

    /**
     * 用户授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 用户认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String userName = usernamePasswordToken.getUsername();
        SystemAdminMapper systemAdminMapper = SpringBeanManager.getBean(SystemAdminMapper.class);
        Map userInfoMap = systemAdminMapper.findByLoginName(userName);
        // 判断用户是否存在
        if (ObjectUtils.isEmpty(userInfoMap)) {
            throw new UnknownAccountException("用户不存在");
        } else if (!(boolean)userInfoMap.get("disabled_flag")) {
            throw new BusinessException("账号已禁用");
        }

        //以下数据属于数据库中的用户名密
        return new SimpleAuthenticationInfo(new AdminUserSession(userInfoMap),
                userInfoMap.get("password"), getName());
    }
}
