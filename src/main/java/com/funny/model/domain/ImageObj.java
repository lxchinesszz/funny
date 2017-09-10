package com.funny.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by mac on 2017/6/26.
 */
@Data
@Builder
@Document(collection = "funny_img")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"id", "fileName"})
public class ImageObj {
    @Id
    private String id;

    /**
     * 图片名称，主要用于管理员上传图片，获取共有连接，不对外暴露
     */
    private String fileName;
    /**
     * 日期
     */
    private String date;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 作者
     */
    private String author;

    /**
     * 一句话
     */
    private String text;

    /**
     * 目前是一个图片，以后可以为视频或者其他url
     */
    private String metaUrl;

    /**
     * type:1.视频 2.网页 3.帖子
     */
    private String type;
}
