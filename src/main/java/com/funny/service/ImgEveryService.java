package com.funny.service;

import com.funny.model.domain.ImageObj;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mac on 2017/6/26.
 */
@Service
public interface ImgEveryService {

    /**
     * 随机获取图片
     * //TODO 随机方法，未定义
     * @return
     */
    ImageObj getImage();
    /**
     * 获取多个图片
     * @return
     */
    List<ImageObj> getImageAsList();

    /**
     * 获取上传图片的token
     * @param fileName 文件名
     * @return
     */
    String getImageToken(String fileName);
}
