package com.education.mapper.system;

import com.education.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/30 20:50
 */
public interface SystemRoleMenuMapper extends BaseMapper {

    List<Map> getMenuListByRoleIds(Map params);
}
