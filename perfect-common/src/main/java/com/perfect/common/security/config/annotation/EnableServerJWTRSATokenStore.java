package com.perfect.security.config.annotation;

import com.perfect.security.token.ServerJWTRSATokenStore;
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
@Import(ServerJWTRSATokenStore.class)
public @interface EnableServerJWTRSATokenStore {
}
