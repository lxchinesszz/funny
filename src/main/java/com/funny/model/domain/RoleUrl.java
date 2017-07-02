package com.funny.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liuxin on 2017/7/1.
 */
@Data
@Builder
@Document(collection = "funny_role_url")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {""})
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RoleUrl {
    @Id
    private String _id;
    /**
     * 对应的用户Id
     */
    private String aid;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 是否开启
     */
    private boolean lock;
    /**
     * 角色级别:
     * 设计思路:
     * 不同的角色拥有不同的权限
     */
    private int level;
    /**
     * 该用户对应url-权限码
     */
    private Map<String,String> urlAndPermisCode;

}
