package com.perfect.common.utils.fs;

import java.io.*;

import com.alibaba.fastjson.JSON;
import com.perfect.bean.pojo.fs.UploadFileResultPojo;
import com.perfect.common.exception.BusinessException;
import com.perfect.common.properies.PerfectConfigProperies;
import org.springframework.http.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class HttpHelper {

    private static PerfectConfigProperies perfectConfigProperies;
    @Autowired
    public void setProperties(PerfectConfigProperies perfectConfigProperies) {
        HttpHelper.perfectConfigProperies = perfectConfigProperies;
    }

    private static RestTemplate restTemplate;
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        HttpHelper.restTemplate = restTemplate;
    }

    public static ResponseEntity<UploadFileResultPojo> executeUpload(MultipartFile multipartFile){
        HttpEntity<MultipartFile> requestEntity = new HttpEntity<>(multipartFile);
        String url = perfectConfigProperies.getFsUrl();
        ResponseEntity<UploadFileResultPojo> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, UploadFileResultPojo.class);
        } catch (Exception ex) {
            log.debug("执行文件上传出错：");
            log.debug(" url：" + url);
            log.debug(" requestEntity：" + JSON.toJSONString(requestEntity));
            throw  new BusinessException("执行文件上传出错。");
        }

        if (response == null) {
            throw  new BusinessException("执行文件上传后，返回结果为空。");
        }
        return response;
    }
}
