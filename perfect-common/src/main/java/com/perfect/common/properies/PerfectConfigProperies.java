package com.perfect.common.properies;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "perfect.config")
@Data
public class PerfectConfigProperies {

    private boolean simpleModel;

    private boolean logSaveDb;

    private boolean logPrint;

    private boolean openAopLog;

    // 临时文件夹
    private String saveTempPath;

    private String saveUpdatePath;


}
