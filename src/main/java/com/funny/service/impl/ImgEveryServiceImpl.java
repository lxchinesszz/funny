package com.funny.service.impl;


import com.funny.model.domain.ImageObj;
import com.funny.service.ImgEveryService;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mac on 2017/6/26.
 */
@Service
public class ImgEveryServiceImpl implements ImgEveryService {

    @Autowired
    Environment environment;
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public ImageObj getImage() {
        List<ImageObj> imageObjs = mongoTemplate.findAll(ImageObj.class);
        return imageObjs.get(0);
    }

    @Override
    public List<ImageObj> getImageAsList() {
        List<ImageObj> imageObjList = mongoTemplate.findAll(ImageObj.class);
        return imageObjList;
    }

    @Override
    public String getImageToken(String fileName) {
        String ak = environment.getProperty("qiniu.ak");
        String sk = environment.getProperty("qiniu.sk");
        String bucket = environment.getProperty("qiniu.bucket");
        String key = fileName;
        long expireSeconds = 3600L;
        Auth auth = Auth.create(ak, sk);
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        return auth.uploadToken(bucket, key, expireSeconds, putPolicy);
    }
}
