package com.zxh.starter;

import com.zxh.security.config.annotation.EnableServerJWTRSATokenStore;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.zxh.*"})
@EnableTransactionManagement(proxyTargetClass = true)
@EnableRedisHttpSession
@MapperScan("com.zxh.mapper")
@EntityScan(basePackages = {"com.zxh.*"})
@Slf4j
@EnableScheduling
@EnableServerJWTRSATokenStore
public class AuthServerStarter {

    public static ConfigurableApplicationContext config;

    public static void main(String[] args) {
        log.info("-----------------------启动开始-------------------------");
        SpringApplication.run(AuthServerStarter.class, args);
        log.info("-----------------------启动完毕-------------------------");
    }
}
