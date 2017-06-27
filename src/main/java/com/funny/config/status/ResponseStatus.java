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

    CHECK_APPKEY(1000,"缺少appKey");


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
