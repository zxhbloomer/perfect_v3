package com.perfect.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({PerfectSecurityProperties.class})
@ConfigurationProperties(prefix = "perfect.security")
public class PerfectSecurityProperties {

    // 登录 URL
    private String loginUrl;
    // 免认证静态资源路径
    private String anonResourcesUrl;
    // 记住我超时时间
    private int rememberMeTimeout;
    // 登出 URL
    private String logoutUrl;
    // 主页 URL
    private String indexUrl;
    // 开发者模式，可以跳过验证码
    private Boolean developModel;
    // 错误日志输出简易模式
    private Boolean logSampleModel;


    public Boolean getLogSampleModel() {
        return logSampleModel;
    }

    public void setLogSampleModel(Boolean logSampleModel) {
        this.logSampleModel = logSampleModel;
    }
    public Boolean getDevelopModel() {
        return developModel;
    }

    public void setDevelopModel(Boolean developModel) {
        this.developModel = developModel;
    }



    private ValidateCodeProperties code = new ValidateCodeProperties();

//    private SocialProperties social = new SocialProperties();

    private SessionProperties session = new SessionProperties();


    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getAnonResourcesUrl() {
        return anonResourcesUrl;
    }

    public void setAnonResourcesUrl(String anonResourcesUrl) {
        this.anonResourcesUrl = anonResourcesUrl;
    }

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    public int getRememberMeTimeout() {
        return rememberMeTimeout;
    }

    public void setRememberMeTimeout(int rememberMeTimeout) {
        this.rememberMeTimeout = rememberMeTimeout;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

//    public SocialProperties getSocial() {
//        return social;
//    }

//    public void setSocial(SocialProperties social) {
//        this.social = social;
//    }

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }
}
