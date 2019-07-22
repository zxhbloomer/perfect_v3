package com.perfect.common.utils.fs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

/**
 * 文件下载相关
 *
 * @author zxh
 * @date 2019年 07月22日 20:31:11
 */
@Slf4j
@Component
public class WebFileUtil {
    /**
     * 使用自定义的httpclient的restTemplate
     */
    private RestTemplate httpClientTemplate;


    /**
     * 下载小文件,采用字节数组的方式,直接将所有返回都放入内存中,容易引发内存溢出
     *
     * @param url
     * @param targetDir
     */
    public void downloadLittleFileToPath(String url, String targetDir) {
        downloadLittleFileToPath(url, targetDir, null);
    }

    /**
     * 下载小文件,直接将所有返回都放入内存中,容易引发内存溢出
     *
     * @param url
     * @param targetDir
     */
    public void downloadLittleFileToPath(String url, String targetDir, Map<String, String> params) {
        Instant now = Instant.now();
        String completeUrl = addGetQueryParam(url, params);
        ResponseEntity<byte[]> rsp = httpClientTemplate.getForEntity(completeUrl, byte[].class);
        log.info("[下载文件] [状态码] code:{}", rsp.getStatusCode());
        try {
            String path = getAndCreateDownloadDir(url, targetDir);
            Files.write(Paths.get(path), Objects.requireNonNull(rsp.getBody(), "未获取到下载文件"));
        } catch (IOException e) {
            log.error("[下载文件] 写入失败:", e);
        }
        log.info("[下载文件] 完成,耗时:{}", ChronoUnit.MILLIS.between(now, Instant.now()));
    }



    /**
     * 拼接get请求参数
     *
     * @param url
     * @param params
     * @return
     */
    private String addGetQueryParam(String url, Map<String, String> params) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url);
        if (!CollectionUtils.isEmpty(params)) {
            for (Map.Entry<String, ?> varEntry : params.entrySet()) {
                uriComponentsBuilder.queryParam(varEntry.getKey(), varEntry.getValue());
            }
        }
        return uriComponentsBuilder.build().encode().toString();
    }


    /**
     * 创建或获取下载文件夹的路径
     *
     * @param url
     * @param targetDir
     * @return
     */
    public String getAndCreateDownloadDir(String url, String targetDir) throws IOException {
        String filename = url.substring(url.lastIndexOf("/") + 1);
        int i = 0;
        if ((i = url.indexOf("?")) != -1) {
            filename = filename.substring(0, i);
        }
        if (!Files.exists(Paths.get(targetDir))) {
            Files.createDirectories(Paths.get(targetDir));
        }
        return targetDir.endsWith("/") ? targetDir + filename : targetDir + "/" + filename;
    }


}

