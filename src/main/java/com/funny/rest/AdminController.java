package com.funny.rest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liuxin on 2017/6/28.
 * 后台管理系统
 */
@Controller
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    /**
     * 跳转到用户登陆页面
     *
     * @return
     */
    @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
    public String tologin() {
        return "admin";
    }


    /**
     * 权限校验：
     * 获得用户密码和用户名,和是否记住密码，然后交给shiro校验，通过就放行，否则返回登录页面
     *
     * @param username 用户名
     * @param password 用户密码
     * @return
     */
    @RequestMapping(value = "/admin/index", method = RequestMethod.POST)
    public String login(String username, String password, String rememberMe, ModelMap modelMap) {
        logger.debug("用户名:{},用户密码:{}", username, password);
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return "admin";
        }
        //TODO 可以加入对用户密码的加密，暂时这里对加密

        //放入用户名和用户输入密码的加密后的信息
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);

        Subject subject = SecurityUtils.getSubject();

        try {
            if (StringUtils.endsWithIgnoreCase(rememberMe,"on")) {
                //设置记住密码
                usernamePasswordToken.setRememberMe(true);
            }
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException ice) {
            // 捕获密码错误异常
            modelMap.put("message", "password error!");
            return "admin";
        }
        return "index";
    }

    /**
     * 跳转到管理后台页面
     *
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String toAdmin() {
        return "index";
    }

}
