package com.perfect.authServer.config.security.bean;

import com.perfect.authServer.config.security.properties.SecurityProperties;
import com.perfect.authServer.handler.MyLogoutSuccessHandler;
import com.perfect.common.security.session.MyExpiredSessionStrategy;
import com.perfect.common.security.session.MyInvalidSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

@Configuration
public class SessionSecurityBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new MyInvalidSessionStrategy(securityProperties.getSession().getSessionInvalidUrl());
    }

    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return new MyExpiredSessionStrategy(securityProperties.getSession().getSessionExpiredUrl());
    }

    @Bean
    @ConditionalOnMissingBean(org.springframework.security.web.authentication.logout.LogoutSuccessHandler.class)
    public org.springframework.security.web.authentication.logout.LogoutSuccessHandler logoutSuccessHandler(){
        return new MyLogoutSuccessHandler(securityProperties.getSignOutUrl());
    }
}
