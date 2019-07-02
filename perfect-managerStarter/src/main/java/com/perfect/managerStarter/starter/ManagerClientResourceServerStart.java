package com.perfect.managerStarter.starter;

import com.perfect.common.security.config.annotation.EnableResource1JWTRSATokenStore;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@SpringBootApplication(scanBasePackages = {"com.perfect.*"})
@EnableTransactionManagement
@EnableRedisHttpSession
@MapperScan("com.perfect.mapper")
@ComponentScan({"com.perfect.manager.controller", "com.perfect.*"})
@EntityScan(basePackages = {"com.perfect.*"})
@Slf4j
@EnableResource1JWTRSATokenStore
@EnableResourceServer
@EnableOAuth2Sso
public class ManagerClientResourceServerStart {

    public static ConfigurableApplicationContext config;

    public static void main(String[] args) {
        log.info("-----------------------启动开始-------------------------");
        ManagerClientResourceServerStart.config = SpringApplication.run(ManagerClientResourceServerStart.class, args);
        List<String> packages = AutoConfigurationPackages.get(config);
        System.out.println("packages: "+packages);
        log.info("-----------------------启动完毕-------------------------");
    }
}
