//package com.perfect.config.security.entryPoint;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.perfect.utils.ResponseResultUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component("JwtAuthenticationEntryPoint")
//public class JwtAuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Override
//    public void commence(HttpServletRequest request,
//                         HttpServletResponse response,
//                         AuthenticationException exception) throws IOException, ServletException {
////        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Authentication token was either missing or invalid.");
//        ResponseResultUtil.responseWriteError(objectMapper,request,response,exception, HttpStatus.UNAUTHORIZED.value());
//    }
//}