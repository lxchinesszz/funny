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

    ERROR(10000,"处理失败"),

    EXIST_USER(10000,"该用户手机号已经注册"),

    CHECK_APPKEY(1000,"缺少appKey"),

    CHECK_USERID(1002,"检查用户唯一标识"),

    CHECK_USERPHONE(1001,"手机号不能为空"),
    CHECK_USER_PASS(1004,"请输入用户密码"),

    CHECK_USERPHONE_OR_PASS(1003,"请检查用户手机号或者用户密码");


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
