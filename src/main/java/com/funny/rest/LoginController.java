package com.funny.rest;

import com.funny.model.domain.User;
import com.funny.service.UserService;
import com.funny.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mac on 2017/6/27.
 */
@RestController
public class LoginController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/funnyanimal/login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseBuilder.IResponseVo login(@RequestBody User user) {
        String userLoginMsg = checkUserLoginInfo(user);
        if (!StringUtils.isEmpty(userLoginMsg)) {
            return ResponseBuilder.ERRORByJackson(10001, userLoginMsg);
        }
        User login = userService.login(user);
        return ResponseBuilder.SUCCESSByJackson(login);
    }

    public String checkUserLoginInfo(User user) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(user.getUid())) {
            sb.append("[uid不能为空]");
            if (StringUtils.isEmpty(user.getName())) {
                sb.append("[用户名不能为空]");
            }
        }
        if (StringUtils.isEmpty(user.getIconurl())) {
            sb.append("用户头像地址非法");
        }
        return sb.toString();
    }
}
