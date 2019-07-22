package com.perfect.common.utils.fs;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.perfect.bean.pojo.fs.UploadFileResultPojo;
import com.perfect.common.exception.BusinessException;
import com.perfect.common.properies.PerfectConfigProperies;
import com.perfect.common.utils.string.StringUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * @author zxh
 */
@Slf4j
public class HttpHelper {

    private static final String UPLOAD_FILE_PATTERN =
        "(jpg|jpeg|png|gif|bmp|doc|docx|xls|xlsx|pdf|txt|rar|zip|7z|mp4|ogg|swf|webm)$";
    private static Pattern pattern = Pattern.compile(UPLOAD_FILE_PATTERN);

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

    /**
     * 多个文件上传
     * @param multipartFile
     * @return
     */
    public static ResponseEntity<List<UploadFileResultPojo>> uploadFiles(Collection<MultipartFile> multipartFiles){
        List<UploadFileResultPojo> fileModels = Lists.newArrayList();
        ResponseEntity<List<UploadFileResultPojo>> response = new ResponseEntity<List<UploadFileResultPojo>>();
        List<UploadFileResultPojo> lstUploadFileResultPojo = new ArrayList<UploadFileResultPojo>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty()) {
                continue;
            } else if (multipartFile.getSize() == 0L){
                continue;
            }
            String filename = getFileName(multipartFile.getOriginalFilename());
            if(validateUploadFileType(filename)) {
                log.debug("文件名：", filename);
                UploadFileResultPojo pojo = new UploadFileResultPojo();
                pojo = uploadFile(multipartFile).getBody();
                lstUploadFileResultPojo.add(pojo);
            }
        }
        return response;
    }

    /**
     * 单个文件上传
     * @param multipartFile
     * @return
     */
    public static ResponseEntity<UploadFileResultPojo> uploadFile(MultipartFile multipartFile){
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

    /**
     * 判断是否允许上传
     *
     * @param fileName
     * @return
     */
    public static boolean validateUploadFileType(String fileName) {
        Matcher matcher = pattern.matcher(getFileSuffix(fileName));
        return matcher.matches();
    }

    /**
     * 根据路径返回文件后缀，如：http://aaa/bbb.jpg C:/aaa/abc.jpg 返回jpg
     *
     * @param pathToName
     * @return
     */
    public static String getFileSuffix(String pathToName) {
        return FilenameUtils.getExtension(pathToName);
    }

    public static String getFileType(File file) {
        try {
            return Files.probeContentType(file.toPath());
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 根据路径返回文件名，如：http://aaa/bbb.jpg C:/aaa/abc.jpg 返回abc.jpg
     *
     * @param pathToName
     * @return
     */
    public static String getFileName(String pathToName) {
        String fileName = FilenameUtils.getName(pathToName);
        return StringUtil.isEmpty(fileName) || "0".equals(fileName) ? null : fileName;
    }

    /**
     * 根据路径返回文件名，如：http://aaa/bbb.jpg C:/aaa/abc.jpg 返回abc
     *
     * @param pathToName
     * @return
     */
    public static String getFileBaseName(String pathToName) {
        String fileBaseName =  FilenameUtils.getBaseName(pathToName);
        return StringUtils.isEmpty(fileBaseName) || "0".equals(fileBaseName) ? null : fileBaseName;
    }

    /**
     * 查看文件是否是图片
     * @param file
     * @return
     */
    public static boolean isImage(File file) {
        try {
            Image image = ImageIO.read(file);
            if (image == null) {
                return false;
            } else {
                return true;
            }
        } catch(IOException ex) {
            return false;
        }
    }


}
