package com.education.admin.api.shiro;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro bean 实例配置
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/22 11:00
 */
@Configuration
@AutoConfigureAfter(ShiroLifecycleBeanPostProcessorConfig.class)
public class ShiroBeanConfig {

    private static final long INVALID_TIME = 3600 * 6 * 1000;

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/test", "anon");
        filterChainDefinitionMap.put("/getUser", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilterFactoryBean.setFilters(shiroFilterFactoryBean.getFilters());
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(Realm systemRealm,
                                           CacheManager ehCacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(systemRealm);
        securityManager.setCacheManager(ehCacheManager);
        return securityManager;
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        // 设置session
        sessionManager.setGlobalSessionTimeout(INVALID_TIME);
        return sessionManager;
    }

    @Bean
    public CacheManager ehCacheManager(net.sf.ehcache.CacheManager cacheManager) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }

    @Bean
    public net.sf.ehcache.CacheManager cacheManager() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("ehcache.xml");
        return net.sf.ehcache.CacheManager.create(inputStream);
    }

    /**
     * 开启Apo 权限注解
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
}
