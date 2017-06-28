package com.funny.service;

import com.funny.model.domain.User;
import org.springframework.stereotype.Service;



/**
 * Created by liuxin on 2017/6/27.
 */
@Service
public interface UserService {
    /**
     * qq联合登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 校验用户手机号是否已经注册
     * @param userPhone
     * @return
     */
    boolean isRegister(String userPhone);

    /**
     * 本平台用户注册
     * @param user
     * @return
     */
    User register(User user);

    /**
     * 本平台用户登陆
     * @param userPhone 用户手机号
     * @param password  用户密码
     * @return
     */
    User login(String userPhone,String password);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    User updateUser(User user);
}
