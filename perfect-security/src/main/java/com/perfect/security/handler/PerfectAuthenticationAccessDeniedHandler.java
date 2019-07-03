package com.perfect.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfect.common.constant.PerfectConstant;
import com.perfect.common.utils.CommonUtil;
import com.perfect.common.utils.ResponseResultUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;

public class PerfectAuthenticationAccessDeniedHandler implements AccessDeniedHandler {

    private ObjectMapper mapper = new ObjectMapper();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {

        if (CommonUtil.isAjaxRequest(request)) {
            response.setContentType(PerfectConstant.JSON_UTF8);
//            response.getWriter().write(this.mapper.writeValueAsString(ResponseBo.error("没有该权限！")));
            ResponseResultUtil.responseWriteError(mapper,request,response,new Exception("没有该权限！"), HttpStatus.INTERNAL_SERVER_ERROR.value());
        } else {
            redirectStrategy.sendRedirect(request, response, PerfectConstant.FEBS_ACCESS_DENY_URL);
        }
    }
}
