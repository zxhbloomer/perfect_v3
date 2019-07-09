package com.perfect.common.Enum;


public enum ResultEnum {
    UNKONW_ERROR(-9, "未知错误"),
    OK(0, "成功"),
    FAIL(-1, "失败"),
    CODE_ERROT(-1,"验证码验证失败"),
    SESSION_INVALID(-1,"session失效"),
    ACCESS_DENIED(-1,"您没有该权限！")
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
