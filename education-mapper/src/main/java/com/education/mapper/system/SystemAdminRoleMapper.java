package com.education.mapper.system;

import com.education.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/30 20:39
 */
public interface SystemAdminRoleMapper extends BaseMapper {

    List<Map> findRoleListByAdminId(Integer adminId);
}
