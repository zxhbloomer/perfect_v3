package com.perfect.redis.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.session.RedisSessionProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.time.Duration;


//maxInactiveIntervalInSeconds 默认是1800秒过期
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds=1800)
@Configuration
@EnableConfigurationProperties(RedisSessionProperties.class)
public class RedisSessionConfiguration {

    @Value("${spring.redis.lettuce.pool.max-active}")
    private Integer maxActive;
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private Integer maxIdle;
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private Long maxWait;
    @Value("${spring.redis.lettuce.pool.min-idle}")
    private Integer minIdle;
    @Value("${spring.redis.commandtimeout}")
    private Long commandTimeOut;
//    @Value("${DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS}")
//    private Long DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS;

//    @Bean
//    public RedisHttpSessionConfiguration redisHttpSessionConfiguration(){
//        RedisHttpSessionConfiguration redisHttpSessionConfiguration = new RedisHttpSessionConfiguration();
////        redisHttpSessionConfiguration.setMaxInactiveIntervalInSeconds(600);
//        com.alibaba.fastjson.support.spring.FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
//        redisHttpSessionConfiguration.setDefaultRedisSerializer(fastJsonRedisSerializer);
//        return redisHttpSessionConfiguration;
//    }
//    @Autowired
//    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
//        this.applicationEventPublisher = applicationEventPublisher;
//    }

//    @Bean
//    public RedisOperationsSessionRepository sessionRepository(@Autowired RedisTemplate redisTemplate, RedisSessionProperties redisSessionProperties) {
//        RedisOperationsSessionRepository sessionRepository =  new RedisOperationsSessionRepository(redisTemplate);
////        RedisTemplate<Object, Object> redisTemplate = this.createRedisTemplate();
//        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
////        RedisOperationsSessionRepository sessionRepository = new RedisOperationsSessionRepository(redisTemplate);
////        sessionRepository.setApplicationEventPublisher(this.applicationEventPublisher);
//        sessionRepository.setDefaultSerializer(fastJsonRedisSerializer);
//        sessionRepository.setDefaultMaxInactiveInterval(this.DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS.intValue());
////        sessionRepository.setRedisKeyNamespace(redisSessionProperties.getNamespace());
//        sessionRepository.setRedisFlushMode(redisSessionProperties.getFlushMode());
//        return sessionRepository;
//    }

    @Bean
    public GenericObjectPoolConfig localPoolConfig() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWait);
        config.setMinIdle(minIdle);
        return config;
    }

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration(RedisProperties redisProperties) {

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(redisProperties.getDatabase());
        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
        redisStandaloneConfiguration.setPort(redisProperties.getPort());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        return redisStandaloneConfiguration;
    }

    @Bean
    public LettuceConnectionFactory connectionFactory(
            RedisStandaloneConfiguration defaultRedisConfig,
            GenericObjectPoolConfig defaultPoolConfig
    ) {
        LettuceClientConfiguration clientConfig =
                LettucePoolingClientConfiguration.builder().commandTimeout(Duration.ofMillis(
                    commandTimeOut))
                        .poolConfig(defaultPoolConfig).build();
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(defaultRedisConfig, clientConfig);
        return connectionFactory;
    }
}

