package com.funny.rest;

import com.funny.config.status.ResponseStatus;
import com.funny.model.domain.ImageObj;
import com.funny.service.ImgEveryService;
import com.funny.util.ResponseBuilder;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mac on 2017/6/26.
 */
@RestController
public class FunnyController {
    @Autowired
    ImgEveryService imgEveryService;

    @RequestMapping(value = "funnyanimal/v1", method = RequestMethod.GET)
    public ResponseBuilder.IResponseVo getImage(String appKey) {
        if (!appKey.equalsIgnoreCase("funny")) {
            return ResponseBuilder.ERRORByJackson(ResponseStatus.CHECK_APPKEY);
        }
        ImageObj image = imgEveryService.getImage();
        return ResponseBuilder.SUCCESSByJackson(image);
    }

    @RequestMapping(value = "funnyanimal/v1/list", method = RequestMethod.GET)
    public ResponseBuilder.IResponseVo getImageAsList(String appKey) {
        if (!appKey.equalsIgnoreCase("funny")) {
            return ResponseBuilder.ERRORByJackson(ResponseStatus.CHECK_APPKEY);
        }
        List<ImageObj> imageAsList = imgEveryService.getImageAsList();
        return ResponseBuilder.SUCCESSByJackson(imageAsList);
    }

    /**
     * 返回token
     *
     * @param
     * @return
     */
    @RequestMapping(value = "funnyanimal/v1/image/token", method = RequestMethod.GET)
    public ResponseBuilder.IResponseVo getImageToken(String fileName) {
        String imageToken = "";
        if (StringUtils.isEmpty(fileName)) {
            imageToken = imgEveryService.getImageToken();
        } else {
            imageToken = imgEveryService.getModifyImageToken(fileName);
        }
        Map jsonObject = new HashMap();
        jsonObject.put("token", imageToken);
        if (StringUtils.isEmpty(imageToken)) {
            return ResponseBuilder.ERRORByJackson(ResponseStatus.ERROR);
        }
        return ResponseBuilder.SUCCESSByJackson(jsonObject);
    }
}
