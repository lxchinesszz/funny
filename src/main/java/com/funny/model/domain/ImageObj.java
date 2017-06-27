package com.funny.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by mac on 2017/6/26.
 */
@Data
@Builder
@Document(collection = "funny_img")
@JsonIgnoreProperties("id")
public class ImageObj {
    @Id
    private String id;

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
}
