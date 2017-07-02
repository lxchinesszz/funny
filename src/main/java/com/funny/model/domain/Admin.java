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
 * Created by liuxin on 2017/7/1.
 * 管理员信息
 */
@Data
@Builder
@Document(collection = "funny_admin")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {""})
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Admin {

    /**
     * 管理员ID
     */
    @Id
    private String _id;

    /**
     * 用户唯一ID
     */
    private String aid;
    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;
}
