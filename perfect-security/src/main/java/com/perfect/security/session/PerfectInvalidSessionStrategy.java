package com.perfect.security.session;

import com.perfect.bean.result.utils.v1.ResponseResultUtil;
import com.perfect.common.exception.PerfectInvalidSessionStrategyException;
import com.perfect.security.properties.PerfectSecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理 session 失效
 */
public class PerfectInvalidSessionStrategy implements InvalidSessionStrategy {

    private PerfectSecurityProperties perfectSecurityProperties;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private boolean createNewSession = true;

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // session过期后自动获取判断
        if (this.perfectSecurityProperties.getCreateNewSession()) {
            request.getSession();
        }
        // if (CommonUtil.isAjaxRequest(request)) {

        ResponseResultUtil.responseWriteError(request, response,
            new PerfectInvalidSessionStrategyException("session会话超时，请登陆后重试！"),
            HttpStatus.UNAUTHORIZED.value(), "session会话超时，请登陆后重试！");

        // }
        // redirectStrategy.sendRedirect(request, response, perfectSecurityProperties.getLogoutUrl());
    }

    public void setPerfectSecurityProperties(PerfectSecurityProperties perfectSecurityProperties) {
        this.perfectSecurityProperties = perfectSecurityProperties;
    }
}
