package com.perfect.security.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 *
 *
 * @description: 授权服务器 TokenStore 配置类，对称加密
 */
public class ServerRedisTokenStore {

    @Autowired
    private LettuceConnectionFactory redisConnectionFactor;

        /**
     * 配置AccessToken的存储方式：此处使用Redis存储
     * Token的可选存储方式
     * 1、InMemoryTokenStore
     * 2、JdbcTokenStore
     * 3、JwtTokenStore
     * 4、RedisTokenStore
     * 5、JwkTokenStore
     */
    @Bean
    public TokenStore tokenStore(LettuceConnectionFactory lettuceConnectionFactory) {
        return new RedisTokenStore(lettuceConnectionFactory);
    }

}
