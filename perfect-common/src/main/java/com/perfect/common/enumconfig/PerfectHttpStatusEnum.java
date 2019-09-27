package com.perfect.common.enumconfig;

/**
 * @author zxh
 * @date 2019/9/27
 */
public enum PerfectHttpStatusEnum {

    SESSION_TIME_OUT(20000, "session会话超时");

    private final int value;

    private final String reasonPhrase;


    PerfectHttpStatusEnum(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

}
