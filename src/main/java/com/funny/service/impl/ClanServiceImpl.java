package com.funny.service.impl;

import com.funny.config.status.ResponseStatus;
import com.funny.model.dao.MongoDao;
import com.funny.model.dao.SayTableDao;
import com.funny.model.domain.*;
import com.funny.service.ClanService;
import com.funny.util.PageUtils;
import com.funny.util.QiniuImages;
import com.funny.util.ResponseBuilder;
import com.funny.util.UUIDUtils;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by mac on 2017/7/17.
 */
@Service
public class ClanServiceImpl implements ClanService {
    @Autowired
    MongoDao mongoDao;
    @Autowired
    SayTableDao sayTableDao;

    @Override
    public void addSay(SayTable sayTable) {
        sayTable.setTimestamp(new Date().getTime());
        sayTable.setTid(UUIDUtils.randomUUID());
        List<String> imgs = sayTable.getImgs();
        List<String> imgUrls = new LinkedList<>();
        if (!ObjectUtils.isEmpty(imgs)) {
            if (imgs.size() > 0) {
                for (String image : imgs) {
                    String qiniuImgUrl = QiniuImages.getQiniuImgUrl(image);
                    imgUrls.add(qiniuImgUrl);
                }
                sayTable.setImgs(imgUrls);
            }
        }
        mongoDao.save(sayTable, SayTable.class);
    }

    @Override
    public void addLeave(String tid, String leaveId, String text, String lid) {
        LeaveTable.LeaveTableBuilder builder = LeaveTable.builder();
        Query tid1 = Query.query(Criteria.where("tid").is(tid));
        String uid = mongoDao.findOne(tid1, SayTable.class).getUid();
        LeaveTable build = null;
        if (StringUtils.isEmpty(lid)) {
            build = builder.tid(tid).uid(uid).leaveId(leaveId).leaveText(text).timestamp(new Date().getTime()).lid(UUIDUtils.randomUUID()).build();
        } else {
            //给评论评论;获取评论的信息，创建子评论，并将子评论id，给评论的fathd
            LeaveTable lid1 = mongoDao.findOne(Query.query(Criteria.where("lid").is(lid)), LeaveTable.class);
            String sonId = UUIDUtils.randomUUID();
            build = builder.uid(uid).leaveId(leaveId).leaveText(text).timestamp(new Date().getTime()).lid(sonId).build();
            lid1.setSubId(sonId);
            mongoDao.save(lid1, LeaveTable.class);
        }
        mongoDao.save(build, LeaveTable.class);
    }

    @Override
    public void pointGood(String tid, String uid) {
        SayTable sayTable = mongoDao.findOne(Query.query(Criteria.where("tid").is(tid)), SayTable.class);
        List praises = sayTable.getPraises();
        praises.add(uid);
        sayTable.setPraises(praises);
        mongoDao.save(sayTable, SayTable.class);

    }

    @Override
    public ResponseBuilder.IResponseVo getSayInfo(String sayUid, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageUtils.buildPageRequest(pageNumber, pageSize);
        List<SayTable> content = null;
        if (!StringUtils.isEmpty(sayUid)) {
            Query query = Query.query(Criteria.where("uid").is(sayUid)).with(pageRequest);
            content = mongoDao.findAll(query, SayTable.class);
        } else {
            Page<SayTable> timestamp = sayTableDao.findAll(pageRequest);
            content = timestamp.getContent();
        }
        List<UserInfoAndSayTable> userInfoAndSayTables = new LinkedList<>();
        for (SayTable sayTable : content) {
            String uid = sayTable.getUid();
            User uid1 = mongoDao.findOne(Query.query(Criteria.where("uid").is(uid)), User.class);
            Map map = new LinkedHashMap();
            map.put("say", sayTable);
            map.put("userInfo", uid1);
            UserInfoAndSayTable build = UserInfoAndSayTable.builder().userInfoSay(map).build();
            userInfoAndSayTables.add(build);
        }
        if (content.size() > 0) {
            return ResponseBuilder.SUCCESSByJackson(userInfoAndSayTables);
        } else {
            return ResponseBuilder.ERRORByJackson(-1, "no centent");
        }
    }


    @Override
    public ResponseBuilder.IResponseVo getLeave(String tid, String type, int pageNumber, int pageSize) {
        if (StringUtils.endsWithIgnoreCase("1", type)) {
            SayTable tid1 = mongoDao.findOne(Query.query(Criteria.where("tid").is(tid)), SayTable.class);
            String uid = tid1.getUid();
            User uid1 = mongoDao.findOne(Query.query(Criteria.where("uid").is(uid)), User.class);
            Map map = new LinkedHashMap();
            map.put("say", tid1);
            map.put("userInfo", uid1);
            return ResponseBuilder.SUCCESSByJackson(map);
        }
        if (StringUtils.endsWithIgnoreCase("2", type)) {
            Query query = Query.query(Criteria.where("tid").is(tid)).skip(pageNumber * pageSize).limit(pageSize);
            query.with(
                    new Sort(Sort.Direction.DESC, "timestamp"));
            List<LeaveTable> tid2 = mongoDao.findAll(query, LeaveTable.class);
            List<Reply> replies = new ArrayList<>(24);
            //TODO 拼装评论信息
            for (LeaveTable lt : tid2) {
                //TODO 获取评论信息信息
                String leaveId = lt.getLeaveId();
                User one = mongoDao.findOne(Query.query(Criteria.where("uid").is(leaveId)), User.class);
                if (ObjectUtils.isEmpty(one)) {
                    continue;
                }
                Reply.ReplyBuilder replyBuilder = Reply.builder();
                replyBuilder.publishGender(one.getGender());
                replyBuilder.publishName(one.getName());
                replyBuilder.publishLogo(one.getIconurl());
                replyBuilder.publishId(lt.getLid());
                replyBuilder.timestamp(lt.getTimestamp() + "");
                replyBuilder.description(lt.getLeaveText());
                if (!StringUtils.isEmpty(lt.getSubId())) {
                    List<Reply> subReplies = new ArrayList<>(24);
                    LeaveTable subLt = mongoDao.findOne(Query.query(Criteria.where("lid").is(lt.getSubId())), LeaveTable.class);
                    if (ObjectUtils.isEmpty(subLt)) {
                        continue;
                    }
                    Reply.ReplyBuilder subReplyBuilder = Reply.builder();
                    String leaveId1 = subLt.getLeaveId();
                    User subOne = mongoDao.findOne(Query.query(Criteria.where("uid").is(leaveId1)), User.class);
                    subReplyBuilder.publishGender(subOne.getGender());
                    subReplyBuilder.publishName(subOne.getName());
                    subReplyBuilder.publishLogo(subOne.getIconurl());
                    subReplyBuilder.publishId(subLt.getLid());
                    subReplyBuilder.timestamp(subLt.getTimestamp() + "");
                    subReplyBuilder.description(subLt.getLeaveText());
                    Reply subReply = subReplyBuilder.build();
                    subReplies.add(subReply);
                    replyBuilder.subPublish(subReplies);
                }
                Reply reply = replyBuilder.build();
                replies.add(reply);
            }
            return ResponseBuilder.SUCCESSByJackson(replies);
        }
        return ResponseBuilder.ERRORByJackson(ResponseStatus.CHECK_APPKEY);
    }
}
