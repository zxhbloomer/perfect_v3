package com.zxh.starter;

import com.zxh.security.config.annotation.EnableResource1JWTRSATokenStore;
import com.zxh.utils.LoggingClientHttpRequestInterceptorUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication(scanBasePackages = {"com.zxh.*"})
@EnableTransactionManagement
@EnableRedisHttpSession
@MapperScan("com.zxh.mapper")
@ComponentScan({"com.zxh.controller", "com.zxh.*"})
@EntityScan(basePackages = {"com.zxh.*"})
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
