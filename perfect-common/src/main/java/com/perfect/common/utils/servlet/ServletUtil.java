package com.perfect.common.utils.servlet;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ServletUtil {

    /**
     * 获取session
     * @return
     */
    public static HttpSession getSession()
    {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getSession();
    }


}
