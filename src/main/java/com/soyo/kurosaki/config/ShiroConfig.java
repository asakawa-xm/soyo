package com.soyo.kurosaki.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

  @Bean(name = "myShiroRealm")
  public ShiroRealm getMyShiroRealm(){
    return new ShiroRealm();
  }

  /**
   * 密码校验规则HashedCredentialsMatcher
   * 这个类是为了对密码进行编码的 ,
   * 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
   * 这个类也负责对form里输入的密码进行编码
   * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
   */
  @Bean("hashedCredentialsMatcher")
  public HashedCredentialsMatcher hashedCredentialsMatcher() {
    HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
    //指定加密方式为MD5............................................................................................
    credentialsMatcher.setHashAlgorithmName("MD5");
    //加密次数
    credentialsMatcher.setHashIterations(1024);
    credentialsMatcher.setStoredCredentialsHexEncoded(true);
    return credentialsMatcher;
  }

  @Bean("authRealm")
  public ShiroRealm authRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
    ShiroRealm authRealm = new ShiroRealm();
    authRealm.setAuthorizationCachingEnabled(false);
    authRealm.setCredentialsMatcher(matcher);
    return authRealm;
  }

  @Bean(name="securityManager")
  public DefaultWebSecurityManager getSecurityManager(@Qualifier("authRealm") ShiroRealm myShiroRealm){
    DefaultWebSecurityManager dwm = new DefaultWebSecurityManager();
    dwm.setRealm(myShiroRealm);
    return dwm;
  }


  @Bean(name = "shiroFilter")
  public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
    ShiroFilterFactoryBean sfb = new ShiroFilterFactoryBean();
    sfb.setSecurityManager(securityManager);
    sfb.setLoginUrl("/index.html");
    sfb.setUnauthorizedUrl("/index.html");

    Map<String, String> filterMap = new LinkedHashMap<String, String>();
//    filterMap.put("/album", "authc");

    sfb.setFilterChainDefinitionMap(filterMap);
    return sfb;
  }

}
