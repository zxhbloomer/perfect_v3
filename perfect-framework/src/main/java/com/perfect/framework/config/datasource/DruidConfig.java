package com.perfect.framework.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.perfect.framework.config.properties.DruidProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * druid 配置多数据源
 *
 * @author ruoyi
 */
@Configuration
public class DruidConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
    public DataSource slaveDataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

//    @Bean(name = "dynamicDataSource")
//    @Primary
//    public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource) {
//        Map<Object, Object> targetDataSources = new HashMap<>(2);
//        targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
//        targetDataSources.put(DataSourceType.SLAVE.name(), slaveDataSource);
//        return new DynamicDataSource(masterDataSource, targetDataSources);
//    }
}