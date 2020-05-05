package com.education.admin.api.controller;
import com.education.common.base.BaseController;
import com.education.common.constants.Constants;
import com.education.common.model.AdminUserSession;
import com.education.common.model.JwtToken;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.service.system.SystemAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员登录接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/26 21:10
 */
@RestController
public class LoginController extends BaseController {

    @Autowired
    private SystemAdminService systemAdminService;
    @Autowired
    private JwtToken jwtToken;

    @PostMapping("login")
    public Result login(@RequestBody Map params) {
        String userName = (String)params.get("userName");
        String password = (String)params.get("password");
        String imageCode = (String)params.get("imageCode");
        String key = String.valueOf(params.get("key"));
        String cacheCode = (String) redisTemplate.opsForValue().get(key);
        if (!imageCode.equalsIgnoreCase(cacheCode)) {
            return Result.fail(ResultCode.FAIL, "验证码错误");
        }
        Result result = systemAdminService.login(userName, password);
        if (result.isSuccess()) {
            // 加载用户菜单及其权限标识
            AdminUserSession adminUserSession = systemAdminService.getAdminUserSession();
            systemAdminService.loadUserMenuAndPermission(adminUserSession);
            params.clear();

            // 创建一个token
            String token = jwtToken.createToken(adminUserSession.getUserMap().get("id"), Constants.FIVE_DAY_TIME_OUT);
            params.put("token", token);
            Map userInfo = new HashMap<>();
            userInfo.put("id", adminUserSession.getUserMap().get("id"));
            userInfo.put("menuList", adminUserSession.getMenuList());
            userInfo.put("permissionList", adminUserSession.getPermissionList());
            userInfo.put("login_name", adminUserSession.getUserMap().get("login_name"));
            params.put("userInfo", userInfo);
            result.setData(params);
        }
        return result;
    }
}
