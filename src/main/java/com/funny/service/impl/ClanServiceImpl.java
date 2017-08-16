package com.funny.service.impl;

import com.funny.model.dao.MongoDao;
import com.funny.model.dao.SayTableDao;
import com.funny.model.domain.LeaveTable;
import com.funny.model.domain.SayTable;
import com.funny.service.ClanService;
import com.funny.util.PageUtils;
import com.funny.util.QiniuImages;
import com.funny.util.ResponseBuilder;
import com.funny.util.UUIDUtils;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
        //TODO 生成TID和日期
        sayTable.setTimestamp(new Date().getTime());
        sayTable.setTid(UUIDUtils.randomUUID());
        List<String> imgs = sayTable.getImgs();
        List<String> imgUrls = new LinkedList<>();
        if (imgs.size() > 0| !ObjectUtils.isEmpty(imgs)) {
            for (String image : imgs) {
                String qiniuImgUrl = QiniuImages.getQiniuImgUrl(image);
                imgUrls.add(qiniuImgUrl);
            }
            sayTable.setImgs(imgUrls);
        }
        mongoDao.save(sayTable, SayTable.class);
    }

    @Override
    public void addLeave(String tid, String uid, String leaveId, String text) {
        LeaveTable.LeaveTableBuilder builder = LeaveTable.builder();
        LeaveTable build = builder.tid(tid).uid(uid).leaveId(leaveId).leaveText(text).date(new Date()).build();
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
    public ResponseBuilder.IResponseVo getSayInfo(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageUtils.buildPageRequest(pageNumber, pageSize);
        Page<SayTable> timestamp = sayTableDao.findAll(pageRequest);
        List<SayTable> content = timestamp.getContent();
        Map map=new LinkedMap();
        map.put("says",content);
        if (content.size() > 0) {
            return ResponseBuilder.SUCCESSByJackson(map);
        } else {
            return ResponseBuilder.ERRORByJackson(-1, "no centent");
        }
    }
}
