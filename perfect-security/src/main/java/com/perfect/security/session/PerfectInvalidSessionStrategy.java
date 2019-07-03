package com.perfect.security.session;

import com.perfect.security.properties.PerfectSecurityProperties;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.InvalidSessionStrategy;

/**
 * 处理 session 失效
 */
public class PerfectInvalidSessionStrategy implements InvalidSessionStrategy {

    private PerfectSecurityProperties securityProperties;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        redirectStrategy.sendRedirect(request, response, securityProperties.getLogoutUrl());
    }

    public void setSecurityProperties(PerfectSecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
