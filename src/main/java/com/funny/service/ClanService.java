package com.funny.service;

import com.funny.model.domain.SayTable;
import com.funny.util.ResponseBuilder;
import org.springframework.stereotype.Service;

/**
 * Created by mac on 2017/7/17.
 */
@Service
public interface ClanService {
    /**
     * 添加说说
     *
     * @param sayTable
     */
    void addSay(SayTable sayTable);

    /**
     * @param tid     说说唯一id
     * @param leaveId 给留言的人的id
     * @param text    留言内存
     * @param type    1.给帖子评论，2给回帖评论
     * @param-uid 留言用户id
     */
    void addLeave(String tid, String leaveId, String text, String type);

    //TODO 点赞

    /**
     * @param tid 说说id
     * @param uid 点赞的用户
     */
    void pointGood(String tid, String uid);

    /**
     * 获取指定时间段说说，根据分页
     *
     * @return
     */
    ResponseBuilder.IResponseVo getSayInfo(int pageNumber, int pageSize);


    ResponseBuilder.IResponseVo getLeave(String tid, String type);
}
