package com.zxh.security.config.annotation;

import com.zxh.security.token.Resource1JWTRSATokenStore;
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
@Import(Resource1JWTRSATokenStore.class)
public @interface EnableResource1JWTRSATokenStore {
}
