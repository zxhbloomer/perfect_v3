package com.perfect.managerStarter.config.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionInterceptor extends HandlerInterceptorAdapter {

    /**
     * 该方法将在Controller处理之前进行调用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("xxxxxxxxxxxxpreHandlepreHandlexxxxxxxxxxxxxxx");
        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
        // TODO： 处理BeforeAction：比如：记录日志、参数验证、权限验证
        // 验证
    }


    /**
     * 在Controller的方法调用之后执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("xxxxxxxxxxxxpostHandlepostHandlexxxxxxxxxxxxxxx");

    }


    /**
     * This implementation is empty.
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
        @Nullable Exception ex) throws Exception {
        System.out.println("xxxxxxxxxxxxafterCompletionxxxxxxxxxxxxxxx");
    }
}