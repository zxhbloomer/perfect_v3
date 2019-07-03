package com.perfect.security.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfect.common.utils.ResponseResultUtil;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 处理 session过期
 * 导致 session 过期的原因有：
 * 1. 并发登录控制
 * 2. 被踢出
 */
public class PerfectExpiredSessionStrategy implements SessionInformationExpiredStrategy {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
//        event.getResponse().getWriter().write(mapper.writeValueAsString(ResponseBo.unAuthorized("登录已失效")));
        ResponseResultUtil.responseWriteError(mapper,event.getRequest(),event.getResponse(),new Exception("登录已失效"), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}
