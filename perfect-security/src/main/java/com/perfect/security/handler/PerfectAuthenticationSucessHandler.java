package com.perfect.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfect.common.constant.PerfectConstant;
import com.perfect.common.utils.result.ResponseResultUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * 登录成功处理器
 */
public class PerfectAuthenticationSucessHandler implements AuthenticationSuccessHandler {

    private ObjectMapper mapper = new ObjectMapper();

    private SessionRegistry sessionRegistry;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();

        response.setContentType(PerfectConstant.JSON_UTF8);
//        response.getWriter().write(mapper.writeValueAsString(ResponseBo.ok()));
        ResponseResultUtil.responseWriteOK(mapper,request,response);
    }
    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }
}
