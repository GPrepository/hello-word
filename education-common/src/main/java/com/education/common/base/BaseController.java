package com.education.common.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * controller 父类
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/26 21:10
 */
public abstract class BaseController {

    @Autowired
    protected RedisTemplate redisTemplate;
}
