package com.perfect.common.security.config.annotation;

import com.perfect.common.security.token.ServerRedisTokenStore;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 *
 * @description: 在启动类上添加该注解来----开启 JWT 令牌存储
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ServerRedisTokenStore.class)
public @interface EnableServerRedisTokenStore {
}
