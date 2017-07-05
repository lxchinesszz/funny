package com.funny.config.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by liuxin on 2017/7/1.
 */
@Configuration
public class ShiroConfig {
    private static final Logger logger= LoggerFactory.getLogger(ShiroConfig.class);


    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     *
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 如果不设置默认会自动寻找Web工程根目录下的"admin登录页面"页面
        shiroFilterFactoryBean.setLoginUrl("/admin/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/css/*", "anon");
        filterChainDefinitionMap.put("/fonts/*", "anon");
        filterChainDefinitionMap.put("/js/*", "anon");
        filterChainDefinitionMap.put("/images/*", "anon");
        filterChainDefinitionMap.put("/admin/login", "anon");//登录页面
        //TODO 跟登录权限,添加权限test测试。
        filterChainDefinitionMap.put("/admin/index", "authc,perms[" +"test" + "]");//校验密码和权限

        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/admin/**", "anon");//登录页面




//        filterChainDefinitionMap.put("/index","authc");
        filterChainDefinitionMap.put("/**", "anon");

        // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->

        //根据给存在的角色赋值权限url赋值权限
//        RoleUrl build = RoleUrl.builder().build();
//        Map<String, String> urlAndPermisCode = build.getUrlAndPermisCode();
//        for (Map.Entry<String,String> urlAndPermis :urlAndPermisCode.entrySet()){
//            String url=urlAndPermis.getKey();
//            String permisCode=urlAndPermis.getValue();
//            filterChainDefinitionMap.put(url, permisCode);
//            logger.info("##################权限【{}】##################",permisCode);
//        }

//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(CookieRememberMeManager cookieRememberMeManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setRememberMeManager(cookieRememberMeManager);
        return securityManager;
    }

    /**
     * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
     *
     * @return
     */
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        return myShiroRealm;
    }


    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }


    /**
     * 设置记住密码cookie
     * @return
     */
    @Bean
    public SimpleCookie getSimpleCookie() {
        SimpleCookie sc = new SimpleCookie("rememberMe");
        sc.setHttpOnly(true);
        sc.setMaxAge(86400000);
        return sc;

    }
    /**
     * cipherKey是加密rememberMe Cookie的密钥；默认AES算法；
     * @return
     */
    @Bean
    public CookieRememberMeManager getCookieRememberMeManager(SimpleCookie simpleCookie){
        CookieRememberMeManager crmm=new CookieRememberMeManager();
        //注入cookie
        crmm.setCookie(simpleCookie);
        //注入cookie值
        crmm.setCipherKey(org.apache.shiro.codec.Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return crmm;
    }
}