package com.funny.util;

import com.funny.model.domain.User;
import org.springframework.util.StringUtils;

/**
 * Created by mac on 2017/6/28.
 */
public class CheckUserUtils {
    /**
     * 校验信息
     * @param user
     * @return
     */
    public static String checkUserLoginInfo(User user) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(user.getUid())) {
            sb.append("[uid不能为空]");
            if (StringUtils.isEmpty(user.getName())) {
                sb.append("[用户名不能为空]");
            }
        }
        if (StringUtils.isEmpty(user.getIconurl())) {
            sb.append("用户头像地址非法");
        }
        return sb.toString();
    }
}
