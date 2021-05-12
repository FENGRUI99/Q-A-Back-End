package com.example.demo.configuration;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加内置过滤器
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/listQuestion","anon");
        filterMap.put("/search","anon");
        bean.setFilterChainDefinitionMap(filterMap);
        //设置登陆页面
        bean.setLoginUrl("/login");
        return bean;
    }

    //securityManager
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);


        return securityManager;
    }

    //创建realm对象
    @Bean
    public  UserRealm userRealm(){
        return  new UserRealm();
    }
}
