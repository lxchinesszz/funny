package com.funny.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Created by mac on 2017/7/17.
 */
@Data
@Builder
@Document(collection = "funny_say")
@JsonIgnoreProperties("id")
public class SayTable {
    @Id
    private String id;
    /**
     * 唯一id
     */
    private String tid;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 文本
     */
    private String text;

    /**
     * 发表图片
     */
    private List<String>imgs;

    /**
     * 点赞的人的id
     */
    private List<String> praises;

    /**
     * 发布日期
     */
    private Date date;

    /**
     * 留言用户
     */
    private String[]leave;
}
