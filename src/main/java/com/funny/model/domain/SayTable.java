package com.funny.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mac on 2017/7/17.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "funny_say")
@JsonIgnoreProperties("id")
public class SayTable {
    @Id
    private String id;

    /**
     * 文章标题
     */
    private String title;
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
    private List<String> imgs;

    /**
     * 点赞的人的id
     */
    private List<String> praises;

    /**
     * 发布日期
     */
    private Long timestamp;

    /**
     * 留言用户
     */
    private String[] leave;

    /**
     * 城市名
     */
    private String cityName;

    /**
     * 使用逗号分隔
     * 21321,43535
     */
    private String location;

    public static void main(String[] args) {
        List<String> imgs = new ArrayList<>();
        imgs.add("test");
        imgs.add("test1");
        SayTable build = SayTable.builder().imgs(imgs).text("tesxts").location("sh").cityName("shagnhai").uid("test").build();

        String s = new GsonBuilder().setPrettyPrinting().create().toJson(build);
        System.out.println(s);
    }
}
