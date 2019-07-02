package com.zxh.config.spring;

import com.zxh.config.messageconverter.CallbackMappingJackson2HttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ActionInterceptorConfig implements WebMvcConfigurer {
//public class ActionInterceptorConfig extends WebMvcConfigurationSupport {

    /**
     * 处理拦截器，主要处理controller中的处理前，中，后
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    /**
     *
     * @param returnValueHandlers
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        // 处理返回值
//        WebMvcConfigurer.super.addReturnValueHandlers(returnValueHandlers);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new CallbackMappingJackson2HttpMessageConverter());
    }
}
