package com.funny.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Package: com.funny.model.domain
 * @Description: 评论的信息
 * @author: liuxin
 * @date: 2017/9/10 下午7:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reply {
    /**
     * 回帖id
     */
    private String publishId;
    /**
     * 回帖人logo
     */
    private String publishLogo;
    /**
     * 回帖人名字
     */
    private String publishName;
    /**
     * 回帖人姓名
     */
    private String publishGender;
    /**
     * 回帖信息
     */
    private String description;
    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * 评论的评论
     */
    private List<Reply> subPublish;
}
