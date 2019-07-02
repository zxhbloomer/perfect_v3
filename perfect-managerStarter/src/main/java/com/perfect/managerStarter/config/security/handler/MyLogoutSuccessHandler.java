package com.perfect.managerStarter.config.security.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfect.common.Enum.ResultEnum;
import com.perfect.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 成功退出
 */
@Slf4j
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    private String signOutSuccessUrl;

    private ObjectMapper objectMapper = new ObjectMapper();

    public MyLogoutSuccessHandler(String signOutSuccessUrl) {
        this.signOutSuccessUrl = signOutSuccessUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("退出成功");

        if (StringUtils.isBlank(signOutSuccessUrl)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(ResultUtil.success(
                    ResultEnum.OK.getCode()
                    ,ResultEnum.OK.getMsg()
                    ,request.getContextPath()
                    ,null
            )));
        } else {
            response.sendRedirect(signOutSuccessUrl);
        }
    }
}
