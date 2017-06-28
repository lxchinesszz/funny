package com.funny.service.impl;

import com.funny.model.dao.MongoDao;
import com.funny.model.domain.User;
import com.funny.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Created by mac on 2017/6/27.
 */
@Service
public class UserServiceImpl implements UserService {
    public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String[] niceName = {"喵星人", "汪星人", "外星人"};

    private static ArrayList<String> iconUrls = new ArrayList<>();

    static {
        iconUrls.add("http://www.qqw21.com/article/UploadPic/2017-6/20176272032040546.jpg");
        iconUrls.add("http://www.qqw21.com/article/UploadPic/2017-6/20176281202053227.png");
        iconUrls.add("http://www.qqw21.com/article/UploadPic/2017-6/20176281202036665.jpg");
    }

    @Autowired
    MongoDao mongoDao;

    /**
     * 当第一次访问，首先查询数据库中是否存在，如果存在直接返回成功
     *
     * @param user 用户对象
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


    /**
     * 校验用户手机号是否已经注册
     *
     * @param userPhone 用户手机号
     * @return
     */
    @Override
    public boolean isRegister(String userPhone) {
        return mongoDao.isExistUserByPhone(userPhone);
    }

    /**
     * 本平台用户登陆
     *
     * @param user
     * @return
     */
    @Override
    public User register(User user) {
        Random random = new Random();
        int index = random.nextInt(3);
        if (StringUtils.isEmpty(user.getName())) {
            user.setName(niceName[index]);
        }
        if (StringUtils.isEmpty(user.getIconurl())) {
            user.setIconurl(iconUrls.get(index));
        }
        String accessToken = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        user.setAccessToken(accessToken);
        return mongoDao.saveUser(user);
    }

    /**
     * 通过用户手机号和密码登录
     *
     * @param userPhone 用户手机号
     * @param password  用户密码
     * @return
     */
    @Override
    public User login(String userPhone, String password) {
        return mongoDao.isExistUser(userPhone, password);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Override
    public User updateUser(User user) {
        User accessToken = mongoDao.findOneByQuery(User.class, "accessToken", user.getAccessToken());
        accessToken.setIconurl(user.getIconurl());
        accessToken.setName(user.getName());
        accessToken.setCity(user.getCity());
        accessToken.setGender(user.getGender());
        accessToken.setPassword(user.getPassword());
        accessToken.setUserPhone(user.getUserPhone());
        return mongoDao.saveUser(accessToken);
    }
}
