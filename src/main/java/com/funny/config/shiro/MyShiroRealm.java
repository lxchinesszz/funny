package com.funny.config.shiro;

import com.funny.model.dao.MongoDao;
import com.funny.model.domain.Admin;
import com.funny.model.domain.RoleUrl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


/**
 * Created by liuxin on 2017/7/1.
 * 当用户进来，首先校验权限，然后校验密码，所以当校验晚密码，就说明成功了
 *
 */
@Component
public class MyShiroRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    MongoDao mongoDao;

    /**
     * 权限链配置
     * 在shiro配置类中把资源对应的权限都加载到应用中
     *
     * 在本方法中,查询用户的所有权限，然后添加
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.debug("##################执行Shiro权限认证##################");
        //获取当前登录输入的用户名，等价于
        String userName = (String) super.getAvailablePrincipal(principals);
        logger.debug("##################开始查询用户【" + userName + "】的权限##################");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //根据每个用户名获得对应的权限列表
        //根据用户名获取用户的权限
        info.addStringPermission("test");
        return info;
    }

    /**
     * 校验用户名和密码
     *
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        logger.debug("身份认证方法：MyShiroRealm.doGetAuthenticationInfo()");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authcToken;
        //TODO 根据用户名和用户密码判断用户，用户验证成功，就把用户名和用户密码放行
        String userName = usernamePasswordToken.getUsername();
        Admin user = mongoDao.findOneByQuery(Admin.class, "userName", usernamePasswordToken.getUsername());
        String pwd = String.valueOf(usernamePasswordToken.getPassword());
        if (ObjectUtils.isEmpty(user)){
            throw new IncorrectCredentialsException();
        }
        if (StringUtils.endsWithIgnoreCase(user.getPassword(), pwd)) {
            return new SimpleAuthenticationInfo(userName, pwd, getName());
        }
        return null;
    }
}
