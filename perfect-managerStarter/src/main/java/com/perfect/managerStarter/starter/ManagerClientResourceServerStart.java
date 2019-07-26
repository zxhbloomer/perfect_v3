package com.perfect.managerstarter.starter;

import com.perfect.security.properties.PerfectSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@SpringBootApplication(scanBasePackages =
        {
                "com.perfect.*",
                "com.perfect.security.*",
                "com.perfect.redis",
                "com.perfect.manager.controller",
        })
@EnableTransactionManagement
@EntityScan(basePackages = {"com.perfect.*"})
@Slf4j
@EnableConfigurationProperties({PerfectSecurityProperties.class})
@EnableCaching
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
