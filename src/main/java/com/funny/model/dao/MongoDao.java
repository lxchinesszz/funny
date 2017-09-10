package com.funny.model.dao;

import com.funny.model.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

/**
 * @Package: elephant.zybank.model.dao
 * @Description:
 * @author: liuxin
 * @date: 17/6/13 上午11:53
 */
@Component
public class MongoDao {
    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * @param t          返回类型
     * @param queryKey   key
     * @param queryValue value
     * @param <T>
     * @return
     */
    public <T> T findOneByQuery(Class<T> t, String queryKey, String queryValue) {
        Query query = Query.query(Criteria.where(queryKey).is(queryValue));
        return mongoTemplate.findOne(query, t);
    }


    /**
     * 判断用户是否存在
     *
     * @param uid
     * @return
     */
    public boolean isExistUser(String uid) {
        Assert.notNull(uid, "用户id不能为空");
        Query query = Query.query(Criteria.where("uid").is(uid));
        User user = mongoTemplate.findOne(query, User.class);
        if (ObjectUtils.isEmpty(user)) {
            return false;
        }
        return true;
    }

    /**
     * 通过用户手机号和密码，判断用户是否存在
     *
     * @param userPhone 用户手机号
     * @param password  用户密码
     * @return
     */
    public User isExistUser(String userPhone, String password) {
        Query query = Query.query(Criteria.where("userPhone").is(userPhone).and("password").is(password));
        User user = mongoTemplate.findOne(query, User.class);
        return user;
    }

    /**
     * 判断用户是否存在
     *
     * @param userPhone
     * @return
     */
    public boolean isExistUserByPhone(String userPhone) {
        Assert.notNull(userPhone, "用户手机号不能为空");
        Query query = Query.query(Criteria.where("userPhone").is(userPhone));
        User user = mongoTemplate.findOne(query, User.class);
        if (ObjectUtils.isEmpty(user)) {
            return false;
        }
        return true;
    }

    /**
     * 保存用户信息
     *
     * @param user
     * @return
     */
    public User saveUser(User user) {
        mongoTemplate.save(user);
        return user;
    }

    /**
     * 保存任意类型
     * @param object
     * @param cls
     * @param <T>
     * @return
     */
    public <T> T save(Object object, Class<T> cls) {
        mongoTemplate.save(object);
        return cls.cast(object);
    }


    /**
     * @param orderId
     * @param t         返回参数
     * @param orderType
     * @param <T>
     * @return
     */
    public <T> T findOneById(String orderId, Class<T> t, String orderType) {
        Assert.notNull(orderId, "订单号不能为空");
        Assert.notNull(orderType, "订单类型不能为空");
        Query query = Query.query(Criteria.where("id").is(orderId));
        String collectionName = "order_" + orderType;
        return mongoTemplate.findOne(query, t, collectionName);
    }

    /**
     * 根据订单号和订单类型
     *
     * @param orderId   订单号
     * @param orderType 订单类型
     */
    public boolean updateById(String orderId, String orderType, Map<String, Object> params) {
        Query query = Query.query(Criteria.where("id").is(orderId));
        Update update = new Update();
        for (Map.Entry<String, Object> map : params.entrySet()) {
            update.set(map.getKey(), map.getValue());
        }
        String collectionName = "order_" + orderType;
        int num = mongoTemplate.updateFirst(query, update, collectionName).getN();
        if (num > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查询单个
     *
     * @param query
     * @param t
     * @param <T>
     * @return
     */
    public <T> T findOne(Query query, Class<T> t) {
        return mongoTemplate.findOne(query, t);
    }

    /**
     * 查询列表
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T> List<T> findAll(Class<T> t) {
        return mongoTemplate.findAll(t);
    }


    /**
     * 查询列表
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T> List<T> findAll(Query query,Class<T> t) {
        return mongoTemplate.find(query,t);
    }

}
