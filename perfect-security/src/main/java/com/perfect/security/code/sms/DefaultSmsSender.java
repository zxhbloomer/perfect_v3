package com.perfect.security.code.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class DefaultSmsSender implements SmsCodeSender {

    @Value("${perfect.security.code.sms.expire-in}")
    private long expireIn;

    @Override
    public void send(String mobile, String code) {
        log.debug("手机号：" + mobile + "的短信验证码为：" + code + "，有效时间：" + expireIn + " 秒");
    }
}
