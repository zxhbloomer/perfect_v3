package com.perfect.security.config;

import com.perfect.common.constant.PerfectConstant;
import com.perfect.core.service.client.user.IMUserService;
import com.perfect.security.code.ValidateCodeGenerator;
import com.perfect.security.code.img.ImageCodeFilter;
import com.perfect.security.code.img.ImageCodeGenerator;
import com.perfect.security.code.sms.DefaultSmsSender;
import com.perfect.security.code.sms.SmsCodeFilter;
import com.perfect.security.code.sms.SmsCodeSender;
import com.perfect.security.cors.CorsFilter;
import com.perfect.security.handler.PerfectAuthenticationAccessDeniedHandler;
import com.perfect.security.handler.PerfectAuthenticationFailureHandler;
import com.perfect.security.handler.PerfectAuthenticationSucessHandler;
import com.perfect.security.handler.PerfectLogoutHandler;
import com.perfect.security.properties.PerfectSecurityProperties;
import com.perfect.security.session.PerfectExpiredSessionStrategy;
import com.perfect.security.session.PerfectInvalidSessionStrategy;
import com.perfect.security.xss.XssFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * security 配置中心
 * @author zxh
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PerfectSecurityConfig extends WebSecurityConfigurerAdapter {
    // 登录成功处理器
    @Autowired
    private PerfectAuthenticationSucessHandler perfectAuthenticationSucessHandler;

    @Autowired
    private IMUserService userDetailService;

    // 登录失败处理器
    @Autowired
    private PerfectAuthenticationFailureHandler perfectAuthenticationFailureHandler;

    @Autowired
    private PerfectSecurityProperties perfectSecurityProperties;

    @Autowired
    private PerfectSmsCodeAuthenticationSecurityConfig perfectSmsCodeAuthenticationSecurityConfig;

    @Qualifier("db1")
    @Autowired
    private DataSource dataSource;

    // spring security自带的密码加密工具类
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 处理 rememberMe 自动登录认证
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String[] anonResourcesUrl = StringUtils.splitByWholeSeparatorPreserveAllTokens(
            perfectSecurityProperties.getAnonResourcesUrl(),",");

        ImageCodeFilter imageCodeFilter = new ImageCodeFilter();
        imageCodeFilter.setAuthenticationFailureHandler(perfectAuthenticationFailureHandler);
        imageCodeFilter.setPerfectSecurityProperties(perfectSecurityProperties);
        imageCodeFilter.afterPropertiesSet();

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        smsCodeFilter.setAuthenticationFailureHandler(perfectAuthenticationFailureHandler);
        smsCodeFilter.setPerfectSecurityProperties(perfectSecurityProperties);
        smsCodeFilter.setSessionRegistry(sessionRegistry());
        smsCodeFilter.afterPropertiesSet();

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler()) // 权限不足处理器
             .and()
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class) // 短信验证码校验
                .addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class) // 添加图形证码校验过滤器
                .formLogin() // 表单方式
                .loginPage(perfectSecurityProperties.getLoginUrl()) // 未认证跳转 URL
                    .usernameParameter("username").passwordParameter("password")
                .loginProcessingUrl(perfectSecurityProperties.getCode().getImage().getLoginProcessingUrl()) // 处理登录认证 URL
                .successHandler(perfectAuthenticationSucessHandler) // 处理登录成功
                .failureHandler(perfectAuthenticationFailureHandler) // 处理登录失败
            .and()
                .rememberMe() // 添加记住我功能
                .tokenRepository(persistentTokenRepository()) // 配置 token 持久化仓库
                .tokenValiditySeconds(perfectSecurityProperties.getRememberMeTimeout()) // rememberMe 过期时间，单为秒
                .userDetailsService(userDetailService) // 处理自动登录逻辑
            .and()
                .sessionManagement() // 配置 session管理器
                .invalidSessionStrategy(invalidSessionStrategy()) // 处理 session失效
                .maximumSessions(perfectSecurityProperties.getMAX_SESSIONS()) // 最大并发登录数量
                .expiredSessionStrategy(new PerfectExpiredSessionStrategy()) // 处理并发登录被踢出
                .sessionRegistry(sessionRegistry()) // 配置 session注册中心
            .and()
            .and()
                .logout() // 配置登出
                .addLogoutHandler(logoutHandler()) // 配置登出处理器
//                .logoutUrl(perfectSecurityProperties.getLogoutUrl()) // 处理登出 url
//                .logoutSuccessUrl("/") // 登出后跳转到 /
                .deleteCookies("JSESSIONID") // 删除 JSESSIONID
            .and()
                .authorizeRequests() // 授权配置
                .antMatchers(anonResourcesUrl).permitAll() // 免认证静态资源路径
                .antMatchers(
                        perfectSecurityProperties.getLoginUrl(), // 登录路径
                        PerfectConstant.FEBS_REGIST_URL, // 用户注册 url
                        perfectSecurityProperties.getCode().getImage().getCreateUrl(), // 创建图片验证码路径
                        perfectSecurityProperties.getCode().getSms().getCreateUrl() // 创建短信验证码路径
                ).permitAll() // 配置免认证路径
                .anyRequest()  // 所有请求
                .authenticated() // 都需要认证
            .and()
                .csrf().disable()
                .apply(perfectSmsCodeAuthenticationSecurityConfig) // 添加短信验证码认证流程
        ; // social 配置
    }


    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
        imageCodeGenerator.setPerfectSecurityProperties(perfectSecurityProperties);
        return imageCodeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsSender();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * 使用 javaconfig 的方式配置是为了注入 sessionRegistry
     * @return
     */
    @Bean
    public PerfectAuthenticationSucessHandler perfectAuthenticationSucessHandler() {
        PerfectAuthenticationSucessHandler authenticationSucessHandler = new PerfectAuthenticationSucessHandler();
        authenticationSucessHandler.setSessionRegistry(sessionRegistry());
        return authenticationSucessHandler;
    }

    /**
     * 配置登出处理器
     * @return
     */
    @Bean
    public LogoutHandler logoutHandler(){
        PerfectLogoutHandler perfectLogoutHandler = new PerfectLogoutHandler();
        perfectLogoutHandler.setSessionRegistry(sessionRegistry());
        return perfectLogoutHandler;
    }

    @Bean
    public InvalidSessionStrategy invalidSessionStrategy(){
        PerfectInvalidSessionStrategy perfectInvalidSessionStrategy = new PerfectInvalidSessionStrategy();
        perfectInvalidSessionStrategy.setPerfectSecurityProperties(perfectSecurityProperties);
        return perfectInvalidSessionStrategy;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new PerfectAuthenticationAccessDeniedHandler();
    }

    /**
     * XssFilter Bean
     */
    @Bean
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public FilterRegistrationBean xssFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*");
        initParameters.put("isIncludeRichText", "true");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }

}
