package com.funny.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by liuxin on 2017/6/27.
 */
@Data
@Builder
@Document(collection = "funny_user")
@JsonIgnoreProperties("id")
public class User {
    /**
     * 用户唯一Id
     */
    private String uid;
    /**
     * 要有校验规则
     * 昵称
     */
    private String userName;
    /**
     * 用户密码使用哈希计算保护
     */
    private String password;
    /**
     * 用户头像
     */
    private String userLogo;
}
