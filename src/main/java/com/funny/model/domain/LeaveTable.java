package com.funny.model.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by mac on 2017/7/17.\
 * 留言
 */
@Data
@Builder
@Document(collection = "funny_leave")
public class LeaveTable {
    @Id
    private String id;

    /**
     * 留言的id
     */
    private String lid;
    /**
     * 说说Id
     */
    private String tid;
    /**
     * 留言人id
     */
    private String uid;
    /**
     * 给留言人的Id
     */
    private String leaveId;
    /**
     * 留言内容
     */
    private String leaveText;
    /**
     * 留言时间
     */
    /**
     * 发布日期
     */
    private Long timestamp;
    /**
     * 评论的评论的id
     * 当对评论的进行评论
     * 这个sonId,就是，对评论的id
     */
    private String subId;
}
