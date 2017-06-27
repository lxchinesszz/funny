package com.funny.service.impl;


import com.funny.model.domain.ImageObj;
import com.funny.service.ImgEveryService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
