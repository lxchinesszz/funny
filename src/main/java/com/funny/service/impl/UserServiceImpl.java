package com.funny.service.impl;

import com.funny.model.dao.MongoDao;
import com.funny.model.domain.User;
import com.funny.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mac on 2017/6/27.
 */
@Service
public class UserServiceImpl implements UserService {
    public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    MongoDao mongoDao;

    /**
     * 当第一次访问，首先查询数据库中是否存在，如果存在直接返回成功
     *
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        String uid = user.getUid();
        boolean userFlag = mongoDao.isExistUser(uid);
        //如果返回true说明用户存在，就返回用户信息
        if (userFlag) {
            return user;
        }
        //如果不存在就注册，然后返回
        return mongoDao.saveUser(user);
    }
}
