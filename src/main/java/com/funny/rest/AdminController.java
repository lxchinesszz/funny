package com.funny.rest;

import com.funny.config.status.ResponseStatus;
import com.funny.model.domain.ImageObj;
import com.funny.service.ImgEveryService;
import com.funny.util.ResponseBuilder;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by liuxin on 2017/6/28.
 * 后台管理系统
 */
@Controller
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    ImgEveryService imgEveryService;

    /**
     * 跳转到用户登陆页面
     *
     * @return
     */
    @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
    public String tologin() {
        return "admin";
    }


    /**
     * 权限校验：
     * 获得用户密码和用户名,和是否记住密码，然后交给shiro校验，通过就放行，否则返回登录页面
     *
     * @param username 用户名
     * @param password 用户密码
     * @return
     */
    @RequestMapping(value = "/admin/index", method = RequestMethod.POST)
    public ModelAndView login(String username, String password, String rememberMe, ModelMap modelMap) {
        logger.debug("用户名:{},用户密码:{}", username, password);
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return new ModelAndView("admin");
        }
        //TODO 可以加入对用户密码的加密，暂时这里对加密

        //放入用户名和用户输入密码的加密后的信息
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);

        Subject subject = SecurityUtils.getSubject();

        try {
            if (StringUtils.endsWithIgnoreCase(rememberMe, "on")) {
                //设置记住密码
                usernamePasswordToken.setRememberMe(true);
            }
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException ice) {
            // 捕获密码错误异常
            modelMap.put("message", "password error!");
            return new ModelAndView("admin");
        }

        return new ModelAndView("controller");
    }

    /**
     * 跳转到管理后台页面
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/admin/index/add/image", produces = "application/json")
    public String toAdmin(String fileName, String date, String author, String text) {
        ImageObj imageObj = new ImageObj();
        imageObj.setFileName(fileName);
        imageObj.setDate(date);
        imageObj.setAuthor(author);
        imageObj.setText(text);
        logger.info(new Gson().toJson(imageObj));
        if (StringUtils.isEmpty(imageObj.getFileName())) {
            logger.error("图片名不能为空");
        }
        if (StringUtils.isEmpty(imageObj.getAuthor())) {
            logger.error("作者信息缺失");
        }
        if (StringUtils.isEmpty(imageObj.getDate())) {
            logger.error("请输入发表的日期");
        }
        if (StringUtils.isEmpty(imageObj.getText())) {
            logger.error("请输入文本信息");
        } else {
            ImageObj imageObj1 = imgEveryService.addImage(imageObj);
            if (!ObjectUtils.isEmpty(imageObj1)) {
                return ResponseBuilder.SUCCESS(imageObj1);
            }
        }
        return ResponseBuilder.ERROR(ResponseStatus.CHECK_APPKEY);

    }

    @RequestMapping(value = "/admin/update/image", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public String imgUpdate(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            return "文件不能为空";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = "./";
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return "文件成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "文件上传失败";

    }
}
