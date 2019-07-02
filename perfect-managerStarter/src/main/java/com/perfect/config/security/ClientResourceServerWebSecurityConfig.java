package com.perfect.config.security;

import com.perfect.config.PasswordEncoderSetting;
import com.perfect.config.handler.SimpleAccessDeniedHandler;
import com.perfect.config.security.handler.MyAuthenticationfailureHandler;
import com.perfect.config.security.handler.MyLoginSuccessHandler;
import com.perfect.config.security.properties.SecurityProperties;
import com.perfect.security.entryPoint.RestAuthenticationEntryPoint;
import com.perfect.service.client.user.IMUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

/**
 * Spring Security 安全配置
 */
@Configuration
@EnableWebSecurity
@Order(1)
public class ClientResourceServerWebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 调用service
    @Autowired
    private IMUserService userSecurityService;

    @Autowired
    private MyLoginSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationfailureHandler myAuthenticationFailHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private SimpleAccessDeniedHandler simpleAccessDeniedHandler;

    @Value("${loginPage}")
    private String loginPage;

    @Value("${loginProcessUrl}")
    private String loginProcessUrl;

    //注入数据源
    @Autowired
    private DataSource dataSource;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Override
//    @Autowired
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(this.userSecurityService)
//                .passwordEncoder(PasswordEncoderSetting.PASSWORD_ENCODER);
//    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userSecurityService);
        provider.setPasswordEncoder(PasswordEncoderSetting.PASSWORD_ENCODER);
        return provider;
    }

    //配置多种认证方式，即多个AuthenticationProvider（用ProviderManager的Arrays.asList添加多个认证方法）
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        ProviderManager authenticationManager =
                new ProviderManager(Arrays.asList(
                        daoAuthenticationProvider()
                ));
        // 不擦除认证密码，擦除会导致TokenBasedRememberMeServices因为找不到Credentials再调用UserDetailsService而抛出UsernameNotFoundException
        authenticationManager.setEraseCredentialsAfterAuthentication(false);
        return authenticationManager;
    }

    /**
     * 全局安全性设置
     *
     * @param webSecurity
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/css/**");
        webSecurity.ignoring().antMatchers("/fonts/**");
        webSecurity.ignoring().antMatchers("/images/**");
        webSecurity.ignoring().antMatchers("/vendor/**");
        webSecurity.ignoring().antMatchers("/js/**");
        webSecurity.ignoring().antMatchers
                ("/swagger-ui.html/**",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v2/api-docs/**",
                        "/swagger-resources/configuration/ui/**",
                        "/swagger-resources/configuration/security/**",
                        "/images/**");
    }

    @Bean
    public JdbcTokenRepositoryImpl tokenRepository(){
        JdbcTokenRepositoryImpl j=new JdbcTokenRepositoryImpl();
        j.setDataSource(dataSource);
        return j;
    }

    //    @Override
    //    protected void configure(HttpSecurity http) throws Exception {
    //        http.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);
    //        http.authorizeRequests().anyRequest().denyAll();
    //    }

    /**
     * 表达式								描述
     * hasRole([role])						当前用户是否拥有指定角色。
     * hasAnyRole([role1,role2])			多个角色是一个以逗号进行分隔的字符串。如果当前用户拥有指定角色中的任意一个则返回true。
     * hasAuthority([auth])				等同于hasRole
     * hasAnyAuthority([auth1,auth2])		等同于hasAnyRole
     * Principle							代表当前用户的principle对象
     * authentication						直接从SecurityContext获取的当前Authentication对象
     * permitAll							总是返回true，表示允许所有的
     * denyAll								总是返回false，表示拒绝所有的
     * isAnonymous()						当前用户是否是一个匿名用户
     * isRememberMe()						表示当前用户是否是通过Remember-Me自动登录的
     * isAuthenticated()					表示当前用户是否已经登录认证成功了。
     * isFullyAuthenticated()				如果当前用户既不是一个匿名用户，同时又不是通过Remember-Me自动登录的，则返回true。
     *
     *
     * 方法							说明
     * openidLogin()					用于基于 OpenId 的验证
     * headers()						将安全标头添加到响应
     * cors()							配置跨域资源共享（ CORS ）
     * sessionManagement()				允许配置会话管理
     * portMapper()					    允许配置一个PortMapper(HttpSecurity#(getSharedObject(class)))，其他提供SecurityConfigurer的对象使用 PortMapper 从 HTTP 重定向到 HTTPS 或者从 HTTPS 重定向到 HTTP。
     * 								    默认情况下，Spring Security使用一个PortMapperImpl映射 HTTP 端口8080到 HTTPS 端口8443，HTTP 端口80到 HTTPS 端口443
     * jee()							配置基于容器的预认证。 在这种情况下，认证由Servlet容器管理
     * x509()							配置基于x509的认证
     * rememberMe						允许配置“记住我”的验证
     * authorizeRequests()				允许基于使用HttpServletRequest限制访问
     * requestCache()					允许配置请求缓存
     * exceptionHandling()				允许配置错误处理
     * securityContext()				在HttpServletRequests之间的SecurityContextHolder上设置SecurityContext的管理。 当使用WebSecurityConfigurerAdapter时，这将自动应用
     * servletApi()					    将HttpServletRequest方法与在其上找到的值集成到SecurityContext中。 当使用WebSecurityConfigurerAdapter时，这将自动应用
     * csrf()							添加 CSRF 支持，使用WebSecurityConfigurerAdapter时，默认启用
     * logout()						    添加退出登录支持。当使用WebSecurityConfigurerAdapter时，这将自动应用。默认情况是，访问URL"/ logout"，
     * 								    使HTTP Session无效来清除用户，清除已配置的任何#rememberMe()身份验证，清除SecurityContextHolder，然后重定向到"/login?success"
     * anonymous()						允许配置匿名用户的表示方法。 当与WebSecurityConfigurerAdapter结合使用时，这将自动应用。
     * 								    默认情况下，匿名用户将使用org.springframework.security.authentication.AnonymousAuthenticationToken表示，并包含角色 "ROLE_ANONYMOUS"
     * formLogin()						指定支持基于表单的身份验证。如果未指定FormLoginConfigurer#loginPage(String)，则将生成默认登录页面
     * oauth2Login()					根据外部OAuth 2.0或OpenID Connect 1.0提供程序配置身份验证
     * requiresChannel()				配置通道安全。为了使该配置有用，必须提供至少一个到所需信道的映射
     * httpBasic()						配置 Http Basic 验证
     * addFilterAt()					在指定的Filter类的位置添加过滤器
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //权限控制
//        http.formLogin()
//                .loginPage(loginPage)
//                .loginProcessingUrl(loginProcessUrl)
//                .usernameParameter("username").passwordParameter("password")
//
//                .and()
//                .authorizeRequests()
//                .antMatchers(loginPage,loginProcessUrl).permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .csrf().disable();

        http
                .authorizeRequests()
//                .mvcMatchers("/api/v*/login/**").permitAll()
                .mvcMatchers("/token/v*/**").permitAll()
                .antMatchers("/api/v*/login").permitAll()
                .antMatchers("/api/hello").permitAll()

//                .antMatchers(loginPage).permitAll()
                .anyRequest().authenticated()
                .accessDecisionManager(accessDecisionManager())
                .antMatchers("/").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
//            .and()
//                .exceptionHandling()
//                .accessDeniedHandler(simpleAccessDeniedHandler)
//                .authenticationEntryPoint(restAuthenticationEntryPoint)
        ;
        //登录验证配置post验证
        http
                .formLogin().permitAll()
                //　自定义的登录验证成功或失败后的去向
                .successHandler(myAuthenticationSuccessHandler)
                // .successHandler(appLoginInSuccessHandler)
                .failureHandler(myAuthenticationFailHandler)
                // 页面
                .loginPage(loginPage)
                // 登录处理url
//                .loginProcessingUrl(loginProcessUrl)
        ;
        http
                .logout().permitAll()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler);
        // 禁用csrf防御机制(跨域请求伪造)，这么做在测试和开发会比较方便。
        //session管理
        http
                .sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy)//session失效策略处理
                .maximumSessions(securityProperties.getSession().getMaximumSessions())//最大session并发数量1
                .maxSessionsPreventsLogin(securityProperties.getSession().isMaxSessionsPreventsLogin())//之后的登录踢掉之前的登录
                .expiredSessionStrategy(sessionInformationExpiredStrategy)//并发过期处理
                .sessionRegistry(sessionRegistry)
        ;
        //http缓存
        http
                .requestCache()
                .requestCache(new HttpSessionRequestCache());
        http
                .csrf().disable();
        // token管理
        http
                .rememberMe()
                .tokenValiditySeconds(securityProperties.getRememberMeSeconds())
                .tokenRepository(tokenRepository());
//                .userDetailsService(userSecurityService);

        http.httpBasic().disable();

    }

    /**
     * 决策管理
     * @return
     */
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        // @formatter: off
        List<AccessDecisionVoter<? extends Object>> decisionVoters =
                Arrays.asList(
                        new WebExpressionVoter(), new RoleVoter(), new AuthenticatedVoter()
                        //                        new MinuteBasedVoter()
                );
        // @formatter: on
        return new UnanimousBased(decisionVoters);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
