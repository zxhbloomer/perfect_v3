package com.perfect.managerStarter.config.security;

//
///**
// * Spring Security 安全配置
// */
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//@Slf4j
//public class ManagerClientResourceServer extends ResourceServerConfigurerAdapter {
//
//    @Value("${auth.server.url}")
//    private String checkTokenEndpointUrl;
//
//    @Value("${auth.server.clientId}")
//    private String clientId;
//
//    @Value("${auth.server.clientsecret}")
//    private String secret;
//
//    @Value("${authorization.resources.ids}")
//    private String resourcesIds;
//
//    @Autowired
//    private TokenStore tokenStore;
//
//    @Autowired(required = false)
//    private RemoteTokenServices remoteTokenServices;
//
//    @Autowired
//    private OAuth2ClientProperties oAuth2ClientProperties;
//
//    @Autowired
//    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
//
//    @Autowired
//    private SimpleAccessDeniedHandler simpleAccessDeniedHandler;
//
//    @Bean("authorizationHeaderRequestMatcher")
//    public RequestMatcher authorizationHeaderRequestMatcher() {
//        return new RequestHeaderRequestMatcher("Authorization");
//    }
//
////    @Override
////    public void configure(HttpSecurity http) throws Exception {
////
////        http
////            .authorizeRequests()
//////                .antMatchers("/login").permitAll()
//////                .antMatchers("/res","/res2/res").access("#oauth2.hasScope('select') and hasRole('CLIENT')")
//////                .antMatchers("/api/**").access("#oauth2.hasScope('select') and hasRole('CLIENT')")
////                .mvcMatchers("/api/v*/login/**").permitAll()
////                .mvcMatchers("/token/v*/**").permitAll()
////                .antMatchers("/api/hello").permitAll()
////                .antMatchers("/swagger-ui.html/**",
////                        "/webjars/**",
////                        "/swagger-resources/**",
////                        "/v2/api-docs/**",
////                        "/swagger-resources/configuration/ui/**",
////                        "/swagger-resources/configuration/security/**",
////                        "/images/**").permitAll()
////                //尚未匹配的任何URL都要求用户进行身份验证
////                .anyRequest().authenticated()
////            .and()
////                .exceptionHandling()
////                    .accessDeniedHandler(simpleAccessDeniedHandler)
////                    .authenticationEntryPoint(restAuthenticationEntryPoint)
////            ;
////        http.csrf().disable();
////        http.httpBasic().disable();
////        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////    }
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
////        super.configure(resources);
////        if (StringUtils.isEmpty(oAuth2ClientProperties.getClientId())) {
////            resources.resourceId(oAuth2ClientProperties.getClientId());
////        }
////        if (Objects.nonNull(remoteTokenServices)) {
////            resources.tokenServices(remoteTokenServices);
////        }
//        resources.resourceId(resourcesIds).stateless(false);
//        resources.tokenStore(tokenStore);
//    }
//
////    @Bean
////    public RemoteTokenServices tokenServices(){
////        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
////        remoteTokenServices.setCheckTokenEndpointUrl(checkTokenEndpointUrl);
////        remoteTokenServices.setClientId(clientId);
////        remoteTokenServices.setClientSecret(secret);
////        return remoteTokenServices;
////    }
//
////    @Bean
////    public static AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
////        return new SavedRequestAwareAuthenticationSuccessHandler();
////    }
////
////    @Bean
////    public static AuthenticationFailureHandler myAuthenticationFailureHandler() {
////        return new SavedRequestAwareAuthenticationFailureHandler();
////    }
//
//}
