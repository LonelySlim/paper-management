package com.pm.papermanagement.config;

import com.pm.papermanagement.realm.MyRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
//    MyRealm myRealm;
//
//    @Autowired
//    public void setMyRealm(MyRealm myRealm){
//        this.myRealm = myRealm;
//    }



//    @Bean
//    public DefaultShiroFilterChainDefinition shiroFilterChainDefinition(){
//        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
//
//        //definition.addPathDefinition("/api/user/login","anon");
//        //definition.addPathDefinition("/api/user/register","anon");
//
//        //definition.addPathDefinition("/api/user/logout","logout");
//
//        definition.addPathDefinition("/**","authc");
//
//        return definition;
//    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/api/user/login","anon");
        filterChainDefinitionMap.put("/api/user/register","anon");
        filterChainDefinitionMap.put("/api/user/haha","anon");
        filterChainDefinitionMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //SecurityUtils.setSecurityManager(defaultWebSecurityManager);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("getRealm") Realm myRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        defaultWebSecurityManager.setRealm(myRealm);
        //SecurityUtils.setSecurityManager(defaultWebSecurityManager);
        return defaultWebSecurityManager;
    }

    @Bean
    public Realm getRealm(){
        MyRealm myRealm = new MyRealm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(4);
        myRealm.setCredentialsMatcher(matcher);
        return myRealm;
    }

}
