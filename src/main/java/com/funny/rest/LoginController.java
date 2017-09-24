package com.funny.rest;

import com.funny.config.status.ResponseStatus;
import com.funny.model.domain.User;
import com.funny.service.UserService;
import com.funny.util.CheckUserUtils;
import com.funny.util.ResponseBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 2017/6/27.
 */
@RestController
public class LoginController {
    @Autowired
    UserService userService;

    /**
     * QQ联合登录
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/funnyanimal/ally/login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseBuilder.IResponseVo login(@RequestBody User user) {
        String userLoginMsg = CheckUserUtils.checkUserLoginInfo(user);
        if (!StringUtils.isEmpty(userLoginMsg)) {
            return ResponseBuilder.ERRORByJackson(10001, userLoginMsg);
        }
        User qqRegister = userService.isQQRegister(user.getAccessToken());
        if (ObjectUtils.isEmpty(qqRegister)) {
            User login = userService.login(user);
            return ResponseBuilder.SUCCESSByJackson(login);
        }
        return ResponseBuilder.SUCCESSByJackson(qqRegister);

    }

    /**
     * 校验该用户手机号是否已经注册过
     *
     * @param userPhone
     * @return
     */
    @RequestMapping(value = "/funnyanimal/user/isRegister", method = RequestMethod.POST)
    public ResponseBuilder.IResponseVo isRegister(String userPhone) {
        if (StringUtils.isEmpty(userPhone)) {
            return ResponseBuilder.ERRORByJackson(ResponseStatus.CHECK_USERPHONE);
        }
        if (userService.isRegister(userPhone)) {
            return ResponseBuilder.SUCCESSByJackson();
        }
        return ResponseBuilder.ERRORByJackson(ResponseStatus.EXIST_USER);
    }

    /**
     * 用户注册
     * 用用户手机号唯一标识,当用户名为空，就讲name改为喵星人
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/funnyanimal/user/register", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseBuilder.IResponseVo register(@RequestBody User user) {

        System.out.println(new Gson().toJson(user));
        if (StringUtils.isEmpty(user.getUserPhone())) {
            return ResponseBuilder.ERRORByJackson(ResponseStatus.CHECK_USERID);
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            return ResponseBuilder.ERRORByJackson(ResponseStatus.CHECK_USER_PASS);
        }
        User register = userService.register(user);
//        String accessToken = register.getAccessToken();
//        Map map = new HashMap();
//        map.put("accessToken", accessToken);
        return ResponseBuilder.SUCCESSByJackson(register);
    }

    /**
     * 本平台用户登陆
     * 通过用户手机号和密码验证
     *
     * @return
     * @param-userPhone 用户手机号
     * @param-password 用户密码
     */
    @RequestMapping(value = "/funnyanimal/user/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseBuilder.IResponseVo loginByNative(@RequestBody User user) {
        String userPhone = user.getUserPhone();
        String password = user.getPassword();
        if (StringUtils.isEmpty(userPhone)) {
            return ResponseBuilder.ERRORByJackson(ResponseStatus.CHECK_USERID);
        }
        User login = userService.login(userPhone, password);
        if (ObjectUtils.isEmpty(login)) {
            return ResponseBuilder.ERRORByJackson(ResponseStatus.CHECK_USERPHONE_OR_PASS);
        }
        return ResponseBuilder.SUCCESSByJackson(login);

    }

    /**
     * 修改用户信息
     *
     * @return
     * @param-userPhone 用户手机号
     * @param-name 用户名
     * @param-password 用户密码
     * @param-iconurl 用户头像
     * @param-gender 用户性别
     * @param-province 省
     * @param-city 城市
     */
    @RequestMapping(value = "/funnyanimal/user/update", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseBuilder.IResponseVo updateUserInfo(@RequestBody User user) {
        if (ObjectUtils.isEmpty(user)) {
            return ResponseBuilder.ERRORByJackson(ResponseStatus.CHECK_USERID);
        }
        User updateUser = userService.updateUser(user);
        return ResponseBuilder.SUCCESSByJackson(updateUser);
    }

}
