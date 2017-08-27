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
     * 管理员添加用户图片
     *
     * @return 返回当前添加的图片信息
     */
    ImageObj addImage(ImageObj imageObj);

    /**
     * 随机获取图片
     * 随机方法，未定义
     *
     * @return
     */
    ImageObj getImage();

    /**
     * 获取多个图片
     *
     * @return
     */
    List<ImageObj> getImageAsList();

    /**
     * 获取上传图片的token
     *
     * @param
     * @return
     */
    String getImageToken();

    /**
     * 覆盖文件凭证
     *
     * @param fileModify
     * @return
     */
    String getModifyImageToken(String fileModify);
}
