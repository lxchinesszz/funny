package com.funny.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by liuxin on 2017/6/27.
 */
@Data
@Builder
@Document(collection = "funny_user")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"id","password"})
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class User {
    @Id
    private String _id;
    /**
     * 用户唯一Id
     */
    private String uid;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 性别
     */
    private String gender;
    /**
     * 要有校验规则
     * 昵称
     */
    private String name;
    /**
     * 用户token
     */
    private String accessToken;

    /**
     * 用户头像
     */
    private String iconurl;
    /**
     * 城市
     */
    private String city;
    /**
     * 身份
     */
    private String province;

    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 签名
     */
    private String moto;
}
