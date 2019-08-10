package com.perfect.framework.base.controller.v1;

import com.alibaba.fastjson.JSON;
import com.perfect.excel.bean.importconfig.template.ExcelTemplate;
import com.perfect.excel.upload.PerfectExcelReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * controller父类
 * 
 * @author zhangxh
 */
@Slf4j
public class BaseController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 下载文件，并check是否是excel文件
     * @param fileUrl
     * @return
     * @throws IOException
     */
    public PerfectExcelReader downloadExcelAndImportData(String fileUrl, String jsonConfig) throws IOException {
        ExcelTemplate et = initExcelTemplate(jsonConfig);

        // 文件下载到流
        ResponseEntity<byte[]> rtnResponse = restTemplate.getForEntity(fileUrl, byte[].class);
        InputStream is =  new ByteArrayInputStream(rtnResponse.getBody());

        // 文件分析，判断是否是excel文档
        if (FileMagic.valueOf(is) == FileMagic.OLE2){
            // Office 2003 ，xls
        } else if (FileMagic.valueOf(is) == FileMagic.OOXML) {
            // Office 2007 +，xlsx
        } else {
            // 非excel文档，报错
            throw new IllegalArgumentException("导入的文件不是office excel，请选择正确的文件来进行上传");
        }

        // 2、按模版进行读取数据
        PerfectExcelReader perfectExcelReader = new PerfectExcelReader(is, et);

        return perfectExcelReader;
    }

    /**
     * 获取excel模版
     * @param jsonConfig
     * @return
     */
    public ExcelTemplate initExcelTemplate(String jsonConfig){
        // 1、获取模板配置类
        ExcelTemplate et = JSON.parseObject(jsonConfig, ExcelTemplate.class);
        // 初始化
        et.initValidator();
        return et;
    }


}
