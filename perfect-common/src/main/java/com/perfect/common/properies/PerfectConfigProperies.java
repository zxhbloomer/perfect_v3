package com.perfect.common.properies;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zxh
 */
@Configuration
@ConfigurationProperties(prefix = "perfect.config")
@Data
public class PerfectConfigProperies {

    private boolean simpleModel;

    private boolean logSaveDb;

    private boolean logPrint;

    private boolean openAopLog;

    /**
     * 上传url
     */
    private String fsUrl;

    private String fsAppid;
    private String fsUsername;
    private String fsGroupid;

}
