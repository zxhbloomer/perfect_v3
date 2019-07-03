package com.perfect.managerStarter.starter;

import com.perfect.security.properties.PerfectSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@SpringBootApplication(scanBasePackages = {"com.perfect.*", "com.perfect.security.*"})
@EnableTransactionManagement
@EnableRedisHttpSession
@MapperScan("com.perfect.core.mapper")
@ComponentScan({"com.perfect.manager.controller", "com.perfect.security", "com.perfect.*"})
@EntityScan(basePackages = {"com.perfect.*"})
@Slf4j
@EnableConfigurationProperties({PerfectSecurityProperties.class})
@EnableCaching
@EnableAsync
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
