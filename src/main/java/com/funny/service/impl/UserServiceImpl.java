package com.funny.service.impl;

import com.funny.model.dao.MongoDao;
import com.funny.model.domain.User;
import com.funny.service.UserService;
import com.funny.util.QiniuImages;
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

    public static final String[]provinces={"M78星云","潘多拉星","宜居星球"};

    public static final String []citys={"汪星市","火星市","喵星市"};

    public static final String[] motos={"一直有一个拯救世界的梦。后来和世界聊了聊，世界没理我","小时候，我最喜欢玩捉迷藏，等别人藏好了，我就回家吃饭。"," “你有超能力吗” “有啊 我超喜欢你”"};

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
        Random random = new Random();
        int index = random.nextInt(3);
        String uid = user.getUid();
        if(StringUtils.isEmpty(user.getProvince())){
            user.setProvince(provinces[index]);
        }
        if (StringUtils.isEmpty(user.getCity())){
            user.setCity(citys[index]);
        }
        if (StringUtils.isEmpty(user.getMoto())){
            user.setMoto(motos[index]);
        }
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
        if(StringUtils.isEmpty(user.getProvince())){
            user.setProvince(provinces[index]);
        }
        if (StringUtils.isEmpty(user.getCity())){
            user.setCity(citys[index]);
        }
        if (StringUtils.isEmpty(user.getMoto())){
            user.setMoto(motos[index]);
        }

        String accessToken = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        user.setAccessToken(accessToken);
        String qiniuImgUrl = QiniuImages.getQiniuImgUrl(user.getIconurl());
        user.setIconurl(qiniuImgUrl);
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
        accessToken.setMoto(user.getMoto());
        accessToken.setProvince(user.getProvince());
        return mongoDao.saveUser(accessToken);
    }
}
