package com.perfect.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfect.utils.ResponseResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录失败处理
 *
 */
@Component(value = "myAuthenticationfailureHandler")
public class MyAuthenticationfailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        ResponseResultUtil.responseWriteError(objectMapper,request,response,exception,HttpStatus.UNAUTHORIZED.value());
    }
}