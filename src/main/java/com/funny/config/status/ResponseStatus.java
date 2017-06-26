package com.funny.config.status;

/**
 * @Package: pterosaur.account.modle.status
 * @Description: 响应状态类型
 * 0 处理成功 -1 不明确失败
 * 1000 开始到1050 为检查性错误  CHECK_ 开头
 * 2000 开始到2050 为非法性错误  ILLEGALITY_开头
 * 10000 开始到10050 为自定义错误  CUSTOM_
 * @author: liuxin
 * @date: 17/4/22 上午11:27
 */
public enum ResponseStatus {

    SUCCESS(0, "处理成功"),

    ERROR(1, "响应失败"),

    CHANEL_ERROR(3, "请检查渠道号"),

    EXITS(2, "您输入的缴费号有误，请重新输入\n缴费号码可以查询纸质账单。"),

    HISTORY_ERROR(-2, "暂未查询到该月账单信息\n建议您多使用菠萝觅平台进行水费缴纳有助于帮您统计缴费明细"),

    CHECK_USERID(1004,"请检查userId"),

    CHECK_CHANNEL(1005, "必传项有空值,请检查渠道号:channel"),

    CHECK_CHANNEL_CITYID(1006, "必传项有空值,请检查cityId，和channel"),

    CHECK_CHANNEL_CITYID_CATEGORYID(1007, "必传项有空值,请检查cityId，categoryId和channel"),

    CHECK_APPCODE_USERID_CUSTOMERID(1008, "必传项有空值,请检查AppCode,UserId，和CustomerId"),

    CHENK_TID_USERID(1009,"请检查是否传入用户Id/Tid"),

    SUPPLIER_ERROR(1009,"供应商响应失败"),

    NO_ARREARAGE(-2, "没有查询到欠费"),

    NO_ARREARAGES(-1,"暂未查到该账单欠费信息"),

    IllEGAl(-1, "查询条件非法");


    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    ResponseStatus(ResponseStatus responseStatus) {
        this.code = responseStatus.getCode();
        this.message = responseStatus.getMessage();
    }


    public int code;

    public String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
