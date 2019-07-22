package com.perfect.common.utils.fs;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.perfect.bean.pojo.fs.UploadFileResultPojo;
import com.perfect.bean.pojo.fs.UrlFilePojo;
import com.perfect.common.exception.BusinessException;
import com.perfect.common.properies.PerfectConfigProperies;
import com.perfect.common.utils.CodeGenerator;
import com.perfect.common.utils.string.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zxh
 */
@Slf4j
public class FsDownloadAndUploadUtil {

    private static final String UPLOAD_FILE_PATTERN =
        "(jpg|jpeg|png|gif|bmp|doc|docx|xls|xlsx|pdf|txt|rar|zip|7z|mp4|ogg|swf|webm)$";
    private static Pattern pattern = Pattern.compile(UPLOAD_FILE_PATTERN);

    private static PerfectConfigProperies perfectConfigProperies;

    @Autowired
    public void setProperties(PerfectConfigProperies perfectConfigProperies) {
        FsDownloadAndUploadUtil.perfectConfigProperies = perfectConfigProperies;
    }

    private static RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        FsDownloadAndUploadUtil.restTemplate = restTemplate;
    }

    /**
     * 多个文件上传
     * 
     * @param multipartFiles
     * @return
     */
    public static List<UploadFileResultPojo> uploadFiles(Collection<MultipartFile> multipartFiles) {
        List<UploadFileResultPojo> fileModels = Lists.newArrayList();
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty()) {
                continue;
            } else if (multipartFile.getSize() == 0L) {
                continue;
            }
            String filename = getFileName(multipartFile.getOriginalFilename());
            if (validateUploadFileType(filename)) {
                log.debug("文件名：", filename);
                UploadFileResultPojo pojo = new UploadFileResultPojo();
                pojo = uploadFile(multipartFile);
                fileModels.add(pojo);
            }
        }
        return fileModels;
    }

    /**
     * 单个文件上传
     * 
     * @param multipartFile
     * @return
     */
    public static UploadFileResultPojo uploadFile(MultipartFile multipartFile) {
        HttpEntity<MultipartFile> requestEntity = new HttpEntity<>(multipartFile);
        String url = perfectConfigProperies.getFsUrl();
        UploadFileResultPojo response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, UploadFileResultPojo.class).getBody();
        } catch (Exception ex) {
            log.debug("执行文件上传出错：");
            log.debug(" url：" + url);
            log.debug(" requestEntity：" + JSON.toJSONString(requestEntity));
            throw new BusinessException("执行文件上传出错。");
        }

        if (response == null) {
            throw new BusinessException("执行文件上传后，返回结果为空。");
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
        String fileBaseName = FilenameUtils.getBaseName(pathToName);
        return StringUtils.isEmpty(fileBaseName) || "0".equals(fileBaseName) ? null : fileBaseName;
    }

    /**
     * 查看文件是否是图片
     * 
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
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * 根据远程文件的url下载文件
     * 
     * @param fileUrl
     * @return
     */
    public File downloadFromUrl(String fileUrl) {
        File targetFile = null;
        HttpURLConnection urlConnection = null;
        try {
            UrlFilePojo urlFile = openAndConnectUrl(fileUrl);
            urlConnection = urlFile.getConn();
            targetFile = createTempFile(urlFile.getFileSuffix());
            if (urlFile.getConn().getContentLengthLong() != 0) {
                FileUtils.copyURLToFile(urlFile.getConnUrl(), targetFile);
                urlFile.getConn().disconnect();
                log.debug("即将从路径: {} 下载文件保存至： {}", fileUrl, targetFile.getAbsolutePath());
            } else {
                log.error("从路径: {} 下载文件发生异常", fileUrl);
            }
        } catch (Exception e) {
            if (urlConnection != null) {
                urlConnection.disconnect();
                urlConnection = null;
            }
            log.debug(e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return targetFile;
    }

    private UrlFilePojo openAndConnectUrl(String remoteFileUrl) throws Exception {
        String urlStr = FilenameUtils.getFullPath(remoteFileUrl);
        // URL路径中有/分割的文件名，进行URL编码处理中文，否则不做处理
        String fileName = getFileName(remoteFileUrl);
        if (StringUtils.isNotEmpty(fileName)) {
            urlStr += URLEncoder.encode(fileName, "UTF-8");
        }
        log.debug("即将从地址【{}】下载文件", urlStr);
        URL connUrl = new URL(urlStr);
        HttpURLConnection urlConnection = (HttpURLConnection)connUrl.openConnection();
        urlConnection.setDoInput(true);
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        String suffix = getFileSuffix(remoteFileUrl);
        if (StringUtils.isEmpty(suffix)) {
            BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
            String contentType = HttpURLConnection.guessContentTypeFromStream(bis);
            if (contentType.trim().startsWith("image")) {
                suffix = StringUtils.substringAfter(contentType, "/");
            }
        }
        return UrlFilePojo.builder().remoteFileUrl(remoteFileUrl).conn(urlConnection).connUrl(connUrl)
            .fileName(fileName).fileSuffix(suffix).build();
    }

    /**
     * 创建带后缀临时文件
     * 
     * @param suffix
     * @return
     */
    public static File createTempFile(String suffix) throws IOException {
        File tempFile = null;
        tempFile = File.createTempFile(CodeGenerator.randomChar(4), "." + suffix);
        log.debug("创建的临时文件路径为：{}", tempFile.getAbsolutePath());
        return tempFile;
    }
}
