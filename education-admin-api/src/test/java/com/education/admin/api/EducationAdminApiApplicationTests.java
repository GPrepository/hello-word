package com.education.admin.api;

import com.education.mapper.system.SystemAdminMapper;
import com.education.mapper.system.SystemRoleMapper;
import com.education.service.system.SystemAdminService;
import com.jfinal.kit.HttpKit;
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
    private SystemAdminService systemAdminService;
    @Autowired
    private SystemAdminMapper systemAdminMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testCache() {
        Map params = new HashMap<>();
        params.put("login_name", "testsfs");
        params.put("password", "123456");
        params.put("encrypt", "123456000");
        int result = systemAdminService.save(params);
        System.err.println(params);
       // redisTemplate.opsForValue().set("test", new LoginController(new SystemAdminService()));
      //  System.out.println(redisTemplate.opsForValue().get("test"));
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            HttpKit.get("http://localhost/image?key=test");
        }
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
      //  systemRoleMapper.save(params);
    }
}
