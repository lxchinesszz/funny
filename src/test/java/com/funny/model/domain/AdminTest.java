package com.funny.model.domain;

import com.funny.FunnyAnimalApplication;
import com.funny.util.UUIDUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by liuxin on 2017/7/1.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FunnyAnimalApplication.class)
public class AdminTest {
    private static final Logger logger = LoggerFactory.getLogger(Admin.class);
    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 创建用户，并添加到角色表中
     */
    @Test
    public void addAdmin() {
        Admin.AdminBuilder builder = Admin.builder();
        String aid = UUIDUtils.randomUUID();
        Admin build = builder.userName("admin").password("admin").aid(aid).build();
        mongoTemplate.save(build);
        Admin admin = mongoTemplate.findAll(build.getClass()).get(0);
        logger.debug(admin.toString());

        //TODO 添加到角色表中
        RoleUrl role = new RoleUrl();
        role.setLevel(0);
        role.setLock(true);
        role.setRoleName("超级管理员");
        role.setAid(aid);
        Map map=new LinkedHashMap<String,String>();
        map.put("/index","anon");
        role.setUrlAndPermisCode(map);
        mongoTemplate.save(role);
    }

}