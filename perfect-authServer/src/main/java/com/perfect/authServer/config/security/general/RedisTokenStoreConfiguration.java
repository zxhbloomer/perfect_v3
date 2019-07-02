package com.perfect.authServer.config.security.general;

//@Configuration
//public class RedisTokenStoreConfiguration {
//    @Value("${spring.redis.host}")
//    private String redisHost;
//
//    @Value("${spring.redis.port}")
//    private int redisPort;
//
//    @Value("${spring.redis.database}")
//    private int redisDataBase;
//    @Value("${spring.redis.password}")
//    private String redisPassword;
//
//    @Value("${spring.redis.command.time.out}")
//    private long redisCommandTimeout;
//
//    @Value("${spring.redis.minIdle}")
//    private int redisMinIdle;
//
//    @Value("${spring.redis.maxIdle}")
//    private int redisMaxIdle;
//
//    @Value("${spring.redis.maxTotal}")
//    private int redisMaxTotal;
//
//    @Value("${spring.redis.maxWaitMillis}")
//    private int redisMaxWaitMillis;
//
//    @Value("${spring.redis.minEvictableIdleTimeMillis}")
//    private int redisMinEvictableIdleTimeMillis;
//
//    @Value("${spring.redis.timeBetweenEvictionRunsMillis}")
//    private int redisTimeBetweenEvictionRunsMillis;
//
//    @Value("${spring.redis.testWhileIdle}")
//    private boolean redisTestWhileIdle;
//
////    @Value("${spring.redis.maxWaitMillis}")
////    private int redisMaxWaitMillis;
////
////    @Value("${spring.redis.minEvictableIdleTimeMillis}")
////    private int redisMinEvictableIdleTimeMillis;
////
////    @Value("${spring.redis.timeBetweenEvictionRunsMillis}")
////    private int redisTimeBetweenEvictionRunsMillis;
////
////    @Value("${spring.redis.testWhileIdle}")
////    private boolean redisTestWhileIdle;
//
//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(redisHost);
//        redisStandaloneConfiguration.setPort(redisPort);
//        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPassword));
//        redisStandaloneConfiguration.setDatabase(redisDataBase);
//        LettucePoolingClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
//                .commandTimeout(Duration.ofMillis(redisCommandTimeout))
////                .poolConfig(genericObjectPoolConfig())
//                .build();
//        return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
//    }
//
//    /**
//     * GenericObjectPoolConfig 连接池配置
//     *
//     * @return
//     */
//    public GenericObjectPoolConfig genericObjectPoolConfig() {
//        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
//        config.setMinIdle(redisMinIdle);
//        config.setTestWhileIdle(redisTestWhileIdle);
//        config.setMaxIdle(redisMaxIdle);
//        config.setMaxTotal(redisMaxTotal);
//        config.setMaxWaitMillis(redisMaxWaitMillis);
//        config.setMinEvictableIdleTimeMillis(redisMinEvictableIdleTimeMillis);//the minimum amount of time an object may sit idle in the pool before it is eligible for eviction.
//        config.setTimeBetweenEvictionRunsMillis(redisTimeBetweenEvictionRunsMillis);
//        return config;
//    }
//
//    /**
//     * 配置AccessToken的存储方式：此处使用Redis存储
//     * Token的可选存储方式
//     * 1、InMemoryTokenStore
//     * 2、JdbcTokenStore
//     * 3、JwtTokenStore
//     * 4、RedisTokenStore
//     * 5、JwkTokenStore
//     */
//    @Bean
//    public TokenStore tokenStore(LettuceConnectionFactory lettuceConnectionFactory) {
//////        return new RedisTokenStore(factory);
////        return new InMemoryTokenStore();
//        return new RedisTokenStore(lettuceConnectionFactory);
//    }

//}
