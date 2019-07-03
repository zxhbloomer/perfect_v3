package com.perfect.security.session;

import com.perfect.security.properties.PerfectSecurityProperties;
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

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        redirectStrategy.sendRedirect(request, response, perfectSecurityProperties.getLogoutUrl());
    }

    public void setPerfectSecurityProperties(PerfectSecurityProperties perfectSecurityProperties) {
        this.perfectSecurityProperties = perfectSecurityProperties;
    }
}
