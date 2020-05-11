package com.education.admin.api.controller.system;

import com.education.common.base.BaseController;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.Result;
import com.education.service.system.SystemRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/5/10 14:39
 */
@RestController
@RequestMapping("/system/role")
public class SystemRoleController extends BaseController {

    @Autowired
    private SystemRoleService systemRoleService;

    /**
     * 获取角色列表
     * @param params
     * @return
     */
    @GetMapping
    public Result list(@RequestParam Map params) {
        return systemRoleService.list(params);
    }

    /**
     * 添加或修改角色
     * @param roleMap
     * @return
     */
    @PostMapping
    public Result saveOrUpdate(@RequestBody Map roleMap) {
        boolean updateFlag = false;
        Integer id = (Integer) roleMap.get("id");
        if (ObjectUtils.isNotEmpty(id)) {
            updateFlag = true;
        }
        return systemRoleService.saveOrUpdate(updateFlag, roleMap);
    }

    /**
     * 根据id角色
     * @param roleMap
     * @return
     */
    @DeleteMapping
    public Result deleteById(@RequestBody Map roleMap) {
        return systemRoleService.deleteById((Integer) roleMap.get("id"));
    }

    /**
     * 批量删除角色
     * @param roleIds
     * @return
     */
    @DeleteMapping("batchDeleteByRoleIds")
    public Result batchDeleteByRoleIds(@RequestBody List<Integer> roleIds) {
        return systemRoleService.batchDeleteByRoleIds(roleIds);
    }
}
