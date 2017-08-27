package com.funny.service.impl;


import com.funny.model.domain.ImageObj;
import com.funny.service.ImgEveryService;
import com.funny.util.QiniuImages;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mac on 2017/6/26.
 */
@Slf4j
@Service
public class ImgEveryServiceImpl implements ImgEveryService {

    @Autowired
    Environment environment;
    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public ImageObj addImage(ImageObj imageObj) {
        String qiniuImgUrl = QiniuImages.getQiniuImgUrl(imageObj.getFileName());
        imageObj.setImgUrl(qiniuImgUrl);
        mongoTemplate.save(imageObj);
        return imageObj;
    }

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
    public String getImageToken() {
        String ak = environment.getProperty("qiniu.ak");
        String sk = environment.getProperty("qiniu.sk");
        String bucket = environment.getProperty("qiniu.bucket");
        long expireSeconds = 3600L;
        Auth auth = Auth.create(ak, sk);
        StringMap putPolicy = new StringMap();
        putPolicy.put("insertOnly", 0);
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        return auth.uploadToken(bucket, null, expireSeconds, putPolicy);
    }


    @Override
    public String getModifyImageToken(String fileModify) {
        String ak = environment.getProperty("qiniu.ak");
        String sk = environment.getProperty("qiniu.sk");
        String bucket = environment.getProperty("qiniu.bucket");
        long expireSeconds = 3600L;
        Auth auth = Auth.create(ak, sk);
        StringMap putPolicy = new StringMap();
        putPolicy.put("insertOnly", 0);
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        return auth.uploadToken(bucket, fileModify, expireSeconds, putPolicy);
    }
}
