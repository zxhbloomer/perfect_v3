package com.zxh.config.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxh.security.bean.BootClientDetails;
import com.zxh.utils.ResponseResultUtil;
import com.zxh.wrapper.MyServletRequestWrapper;
import org.apache.catalina.connector.RequestFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 *
 * 认证不带客户端信息参数处理 filter
 *
 */
@Component
public class BootBasicAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.contains(request.getContentType(), "application/json")
                && Objects.equals( request.getServletPath(), "/oauth/token")) {

            BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JSONObject jsonObject = JSONObject.parseObject(responseStrBuilder.toString());
            Map<String, String[]> map = JSON.parseObject(responseStrBuilder.toString(), new TypeReference<Map<String, String[]>>() {});

            HttpServletRequest s = new MyServletRequestWrapper(((HttpServletRequest) request), map);
            filterChain.doFilter(s, response);
        } else {
            filterChain.doFilter(request, response);
        }

//        if (!request.getRequestURI().equals("/oauth/token") ) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String[] clientDetails = this.isHasClientDetails(request);
//
//        if (clientDetails == null) {
////            HttpUtil.writerError( response,HttpStatus.UNAUTHORIZED.value(),"请求中未包含客户端信息");
//            ResponseResultUtil.responseWriteError(objectMapper,request,response,new Exception("请求中未包含客户端信息"), HttpStatus.UNAUTHORIZED.value());
//            return;
//        }
//
//       this.handle(request,response,clientDetails,filterChain);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response, String[] clientDetails,FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            filterChain.doFilter(request,response);
            return;
        }


        BootClientDetails details = (BootClientDetails) this.clientDetailsService.loadClientByClientId(clientDetails[0]);
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(details.getClientId(), details.getClientSecret(), details.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(token);


        filterChain.doFilter(request,response);
    }

    /**
     * 判断请求头中是否包含client信息，不包含返回null
     */
    private String[] isHasClientDetails(HttpServletRequest request) {

        String[] params = null;

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {

            String basic = header.substring(0, 5);

            if (basic.toLowerCase().contains("basic")) {

                String tmp = header.substring(6);
                String defaultClientDetails = new String(Base64.getDecoder().decode(tmp));

                String[] clientArrays = defaultClientDetails.split(":");

                if (clientArrays.length != 2) {
                    return params;
                } else {
                    params = clientArrays;
                }

            }
        }

        String id = request.getParameter("client_id");
        String secret = request.getParameter("client_secret");

        if (header == null && id != null) {
            params = new String[]{id, secret};
        }


        return params;
    }

    public ClientDetailsService getClientDetailsService() {
        return clientDetailsService;
    }

    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }
}
