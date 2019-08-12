package com.perfect.framework.base.controller.v1;

import com.alibaba.fastjson.JSON;
import com.perfect.bean.pojo.fs.UploadFileResultPojo;
import com.perfect.bean.vo.sys.rabc.role.SysRoleVo;
import com.perfect.common.properies.PerfectConfigProperies;
import com.perfect.excel.bean.importconfig.template.ExcelTemplate;
import com.perfect.excel.upload.PerfectExcelReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.File;
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

    @Autowired
    private PerfectConfigProperies perfectConfigProperies;

    /**
     * 错误fileurl
     * @param fileUrl
     * @return
     */
    public UploadFileResultPojo setErrorFile(String fileUrl, String reName){
        // 上传的url
        String uploadFileUrl = perfectConfigProperies.getFsUrl();
        FileSystemResource resource = new FileSystemResource(fileUrl);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", resource);
        param.add("appid", "0");
        param.add("username", "PROFECT");
        param.add("groupid", "ONE");
        /**
         * request 头信息
         */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("multipart/form-data; charset=UTF-8"));
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        UploadFileResultPojo uploadFileResultPojo = restTemplate.exchange(uploadFileUrl, HttpMethod.POST, httpEntity, SysRoleVo.class).getBody();
        // 判断文件是否存在
        File file = new File(fileUrl);
        if (file.exists()) {
            file.delete();
        }
        return uploadFileResultPojo;
    }

    /**
     * 下载excel导入文件，并check是否是excel文件，然后根据模板定义进行导入
     * 如果有错误，则会生成错误excel，供客户下载查看。
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
