package com.funny.service.impl;

import com.funny.model.dao.MongoDao;
import com.funny.model.domain.LeaveTable;
import com.funny.model.domain.SayTable;
import com.funny.service.ClanService;
import com.funny.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by mac on 2017/7/17.
 */
@Service
public class ClanServiceImpl implements ClanService {
    @Autowired
    MongoDao mongoDao;

    @Override
    public void addSay(SayTable sayTable) {
        //TODO 生成TID和日期
        sayTable.setTimestamp(new Date().getTime());
        sayTable.setTid(UUIDUtils.randomUUID());
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
        mongoDao.save(sayTable,SayTable.class);

    }
}
