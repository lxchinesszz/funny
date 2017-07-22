package com.funny.rest;

import com.funny.config.status.ResponseStatus;
import com.funny.model.domain.ImageObj;
import com.funny.model.domain.SayTable;
import com.funny.service.ClanService;
import com.funny.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by mac on 2017/7/17.
 * 社区控制层
 */
@RestController
public class ClanController {
    @Autowired
    ClanService clanService;

    /**
     * 发说说
     * @param sayTable
     * @return
     */
    @RequestMapping(value = "funnyanimal/v1/say", method = RequestMethod.POST)
    public ResponseBuilder.IResponseVo addSay(@RequestBody SayTable sayTable) {
        if (StringUtils.isEmpty(sayTable.getUid())) {
            return ResponseBuilder.ERRORByJackson(ResponseStatus.CHECK_USERID);
        }
        clanService.addSay(sayTable);
        return ResponseBuilder.SUCCESSByJackson();
    }

    /**
     * 回帖
     * @param boby
     * @return
     */
    @RequestMapping(value = "funnyanimal/v1/leave", method = RequestMethod.POST)
    public ResponseBuilder.IResponseVo addSay(@RequestBody Map boby) {
        if (StringUtils.isEmpty((String) boby.get("uid"))) {
            return ResponseBuilder.ERRORByJackson(ResponseStatus.CHECK_USERID);
        }
        String tid = ((String) boby.get("tid"));
        String uid = ((String) boby.get("uid"));
        String leaveId = ((String) boby.get("leaveId"));
        String text = ((String) boby.get("text"));
        clanService.addLeave(tid, uid, leaveId, text);
        return ResponseBuilder.SUCCESSByJackson();
    }

    /**
     * 点赞
     * @param boby
     * @return
     */
    @RequestMapping(value = "funnyanimal/v1/point", method = RequestMethod.POST)
    public ResponseBuilder.IResponseVo point(@RequestBody Map boby) {
        String uid = (String) boby.get("uid");
        String tid = ((String) boby.get("tid"));
        if (StringUtils.isEmpty(uid)) {
            return ResponseBuilder.ERRORByJackson(ResponseStatus.CHECK_USERID);
        }
        if (StringUtils.isEmpty(tid)){
            return ResponseBuilder.ERRORByJackson(10001,"请输入说说id");
        }
        clanService.pointGood(tid,uid);
        return ResponseBuilder.SUCCESSByJackson();
    }
}
