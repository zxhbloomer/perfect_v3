package com.perfect.security.code.sms;

public interface SmsCodeSender {
    void send(String mobile, String code);
}
