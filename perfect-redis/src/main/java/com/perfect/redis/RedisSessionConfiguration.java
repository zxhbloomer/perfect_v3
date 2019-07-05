package com.perfect.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@Configuration
//maxInactiveIntervalInSeconds 默认是1800秒过期
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=1800)
public class RedisSessionConfiguration {

}

