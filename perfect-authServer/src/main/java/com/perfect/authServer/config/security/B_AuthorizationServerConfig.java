package com.perfect.authServer.config.security;

import com.perfect.authServer.config.security.filter.BootBasicAuthenticationFilter;
import com.perfect.common.security.entryPoint.RestAuthenticationEntryPoint;
import com.perfect.core.service.oauth.IOauthClientDetailsService;
import com.perfect.core.service.oauth.IOauthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.client.token.JdbcClientTokenServices;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * OAuth2的配置，认证服务配置，可作为sso的入口
 *
 * 密码模式（resource owner password credentials）(为遗留系统设计)(支持refresh token)
 *
 * 授权码模式（authorization code）(正宗方式)(支持refresh token)
 *
 * 简化模式（implicit）(为web浏览器应用设计)(不支持refresh token)
 *
 * 客户端模式（client credentials）(为后台api服务消费者设计)(不支持refresh token)
 *
 * /oauth/authorize：授权端点
 * /oauth/token：令牌端点
 * /oauth/confirm_access：用户确认授权提交端点
 * /oauth/error：授权服务错误信息端点
 * /oauth/check_token：用于资源服务访问的令牌解析端点
 * /oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话
 *
 * https://www.jianshu.com/p/227f7e7503cb
 *
 */
@Configuration
@EnableAuthorizationServer
public class B_AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TokenStore tokenStore;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private IOauthUserService userDetailsService;

    @Autowired
    private IOauthClientDetailsService clientDetailsService;

    @Autowired(required = false)
    private JdbcClientDetailsService jdbcClientDetailsService;

    @Autowired
    private BootBasicAuthenticationFilter filter;

    //注入数据源
    @Autowired
    private DataSource dataSource;

//    @Autowired
//    private AppConfig appConfig;

    //令牌失效时间
    public int accessTokenValiditySeconds;

    //刷新令牌失效时间
    public int refreshTokenValiditySeconds;

    //是否可以重用刷新令牌
    public boolean isReuseRefreshToken;

    //是否支持刷新令牌
    public boolean isSupportRefreshToken;

    @Autowired
    public B_AuthorizationServerConfig(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.accessTokenValiditySeconds = (int) TimeUnit.DAYS.toSeconds(1);
        this.refreshTokenValiditySeconds = 0;
        this.isReuseRefreshToken = false;
        this.isSupportRefreshToken = false;
    }

//    public B_AuthorizationServerConfig() {
//        this((int) TimeUnit.DAYS.toSeconds(1),
//                0,
//                false,
//                false);
//    }

//    public B_AuthorizationServerConfig(int accessTokenValiditySeconds,
//                                       int refreshTokenValiditySeconds,
//                                       boolean isReuseRefreshToken,
//                                       boolean isSupportRefreshToken) {
//        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
//        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
//        this.isReuseRefreshToken = isReuseRefreshToken;
//        this.isSupportRefreshToken = isSupportRefreshToken;
//    }

    /**
     * 配置授权服务器端点，如令牌存储，令牌自定义，用户批准和授权类型，不包括端点安全配置
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        Collection<TokenEnhancer> tokenEnhancers = applicationContext.getBeansOfType(TokenEnhancer.class).values();
        TokenEnhancerChain tokenEnhancerChain=new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(new ArrayList<>(tokenEnhancers));

        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setReuseRefreshToken(isReuseRefreshToken);
        defaultTokenServices.setSupportRefreshToken(isSupportRefreshToken);
        defaultTokenServices.setTokenStore(tokenStore);
        defaultTokenServices.setAccessTokenValiditySeconds(accessTokenValiditySeconds);
        defaultTokenServices.setRefreshTokenValiditySeconds(refreshTokenValiditySeconds);
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);
        //若通过 JDBC 存储令牌
        if (Objects.nonNull(jdbcClientDetailsService)){
            defaultTokenServices.setClientDetailsService(jdbcClientDetailsService);
        }
        // 授权码jdbc保存
        endpoints.authorizationCodeServices(jdbcAuthorizationCodeServices());
        // 保存Approval
        endpoints.approvalStore(jdbcApprovalStore());
        // 保存oauth_refresh_token
//        endpoints.tokenStore(tokenStore);


        endpoints
                .authenticationManager(authenticationManager)
                // 不配置会导致token无法刷新
                .userDetailsService(userDetailsService)
                .tokenServices(defaultTokenServices)
                .allowedTokenEndpointRequestMethods(HttpMethod.POST,HttpMethod.GET)
                .tokenEnhancer(tokenEnhancerChain)
        ;

        // 判断当前是否使用jwt
//        if(!(tokenStore instanceof RedisTokenStore) && this.converter!=null){
//            endpoints.accessTokenConverter(converter);
//        }

    }


    /**
     * 配置授权服务器端点的安全
     * @param oauthServer
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        // 允许表单登录
        oauthServer.allowFormAuthenticationForClients();

        // 加载client的service
        filter.setClientDetailsService(clientDetailsService);

        // 自定义异常处理端口
        oauthServer.authenticationEntryPoint(restAuthenticationEntryPoint);

        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    /**
     * 配置客户端详情
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        super.configure(clients);
        clients.inMemory()                          // 使用内存存储客户端信息
                .withClient("resource1")       // client_id
                .secret("secret")                   // client_secret
                .authorizedGrantTypes("authorization_code","password")     // 该client允许的授权类型
                .accessTokenValiditySeconds(3600)               // Token 的有效期
                .scopes("read")                    // 允许的授权范围
                .autoApprove(true);                  //登录后绕过批准询问(/oauth/confirm_access)
        clients.withClientDetails(clientDetailsService);
    }

//    @Bean
//    public TokenStore tokenStore() {
//        return new JdbcTokenStore(dataSource);
//    }

//    @Bean
//    @Primary
//    public DefaultTokenServices tokenServices() {
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setSupportRefreshToken(true);
//        tokenServices.setTokenStore(jdbctokenStore());
//        return tokenServices;
//    }

    @Bean
    public JdbcAuthorizationCodeServices jdbcAuthorizationCodeServices(){
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * oauth_client_token
     */
    @Bean
    public ClientTokenServices clientTokenServices() {
        return new JdbcClientTokenServices(dataSource);
    }

    /**
     * oauth_approvals
     */
    @Bean
    public JdbcApprovalStore jdbcApprovalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    /**
     * oauth_refresh_token
     * @return
     */
//    @Bean
//    public JdbcTokenStore jdbctokenStore() {
//        return new JdbcTokenStore(dataSource);
//    }
}