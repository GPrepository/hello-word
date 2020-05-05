package com.education.admin.api;

import com.education.mapper.system.SystemAdminMapper;
import com.education.mapper.system.SystemRoleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EducationAdminApiApplicationTests {

    @Autowired
    private SystemAdminMapper systemAdminMapper;

    @Autowired
    private SystemRoleMapper systemRoleMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testCache() {
       // redisTemplate.opsForValue().set("test", new LoginController(new SystemAdminService()));
      //  System.out.println(redisTemplate.opsForValue().get("test"));
    }

    @Test
    @Transactional
    public void contextLoads() {
        Map params = new HashMap<>();
        params.put("admin_id", 1);
        params.put("role_id", 2);
        systemAdminMapper.save(params);
        this.saveRole();
        int i = 1 / 0;
    }

   // @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveRole() {
        Map params = new HashMap<>();
        params.put("name", "test");
        systemRoleMapper.save(params);
    }
}
