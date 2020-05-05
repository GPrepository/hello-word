package com.education.service.system;

import com.education.mapper.system.SystemMenuMapper;
import com.education.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理业务层
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/30 20:36
 */
@Service
public class SystemMenuService extends BaseService<SystemMenuMapper> {

    public List<Map> queryList(Map params) {
        return mapper.queryList(params);
    }
}
