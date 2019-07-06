package com.perfect.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.time.Duration;

@Configuration
//maxInactiveIntervalInSeconds 默认是1800秒过期
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=1800)
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
    private Long commandtimeout;



    @Bean
    public GenericObjectPoolConfig localPoolConfig(RedisProperties redisProperties) {
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
                LettucePoolingClientConfiguration.builder().commandTimeout(Duration.ofMillis(commandtimeout))
                        .poolConfig(defaultPoolConfig).build();
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(defaultRedisConfig, clientConfig);
        return connectionFactory;
    }
}

