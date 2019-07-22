package com.perfect.common.utils.fs;

import com.google.common.collect.Lists;
import com.perfect.common.utils.string.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用途：文件工具类
 * 作者: zxh
 * 时间: 2018/3/12  20:45
 */
@Slf4j
@Component
public class AppFileUtil {

    private static final String UPLOAD_FILE_PATTERN =
            "(jpg|jpeg|png|gif|bmp|doc|docx|xls|xlsx|pdf|txt|rar|zip|7z|mp4|ogg|swf|webm)$";
    private static Pattern pattern = Pattern.compile(UPLOAD_FILE_PATTERN);

    @Autowired
    private AppConfig config;

    public static StoreLocation serverUploadLocation = null;

    public enum StoreLocation {disk, fastdfs, baidubos}

    @PostConstruct
    public void init() {
        serverUploadLocation = Enum.valueOf(StoreLocation.class, config.getUploadLocation());
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

    /**
     * 上传单个文件
     * @param directory 相对路径
     * @param multipartFile
     * @return
     * @throws Exception
     */
    public SysFile uploadFile(String directory, MultipartFile multipartFile) throws Exception {
        return uploadFiles(directory, Arrays.asList(multipartFile)).get(0);
    }

    /**
     * 上传多个文件
     * @param directory 相对路径
     * @param multipartFiles
     * @return
     * @throws Exception
     */
    public List<SysFile> uploadFiles(String directory, Collection<MultipartFile> multipartFiles) throws Exception {
        Assert.notEmpty(multipartFiles, "Upload file can not empty!");
        List<SysFile> fileModels = Lists.newArrayList();
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty()) {
                continue;
            } else if (multipartFile.getSize() == 0L){
                continue;
            }
            String filePath = null;
            String filename = getFileName(multipartFile.getOriginalFilename());
            //特殊字符过滤，防止XSS漏洞
            filename = JacksonUtils.escapeString(filename);
            if(validateUploadFileType(filename)) {
                log.debug("Will upload file {} to {}", filename, serverUploadLocation);
                switch (serverUploadLocation) {
                    case disk:
                        File targetFileDirectory = createUploadStorePath(directory);
                        byte[] bytes = multipartFile.getBytes();
                        Path path = Paths.get(targetFileDirectory.getPath() + ApplicationConstants.SLASH + filename);
                        Files.write(path, bytes);
                        filePath = path.toString();
                        break;
                    case fastdfs:
                        filePath = FastDfsClient.uploadFile(IOUtils.toByteArray(multipartFile.getInputStream()),
                                filename, getFileSuffix(filename));
                        break;
                }
                SysFile sysFile = SysFile.builder().fileName(filename).fileType(getFileSuffix(filename))
                        .filePath(StringUtils.replace(filePath, ApplicationConstants.SEPARATOR, ApplicationConstants.SLASH))
                        .fileSize(multipartFile.getSize()).downLoadUrl(SysFileController.DOWNLOAD_URL_DATABASE).
                                build();
                log.debug("上传文件成功，具体信息如下： {}", sysFile.toString());
                fileModels.add(sysFile);
            } else {
                log.error("File {} upload is forbidden type, can not upload to server!", filename);
            }
        }
        return fileModels;
    }

    /**
     * 将文件在远程URL读取后，再上传
     * @param fileUrl   远程文件URL
     * @param fileName  存储文件名称
     * @param directory 相对路径
     * @return 返回存储路径
     */
    public SysFile uploadFromUrl(String fileUrl, String directory) throws Exception {
        String filePath = null;
        String fileName = null;
        Long fileSize = null;
        HttpURLConnection urlConnection = null;
        UrlFile urlFile = null;
        try {
            urlFile = openAndConnectUrl(fileUrl);
            urlConnection = urlFile.getConn();
            if(StringUtils.isEmpty(urlFile.getFileName())){
                //URL连接上面没有文件名
                fileName = CodeGenerator.systemUUID();
            }
            fileName = fileName + ApplicationConstants.DOT + urlFile.getFileSuffix();
            switch (serverUploadLocation) {
                case disk:
                    File targetFileDirectory = createUploadStorePath(directory);
                    File storeFile = new File(targetFileDirectory.getAbsolutePath()
                            + ApplicationConstants.SLASH + fileName);
                    FileUtils.touch(storeFile); //覆盖文件
                    FileUtils.copyURLToFile(urlFile.getConnUrl(), storeFile);
                    filePath = storeFile.getAbsolutePath();
                    fileSize = storeFile.length();
                    break;
                case fastdfs:
                    File tmpFile = createTempFile();
                    FileUtils.copyURLToFile(urlFile.getConnUrl(), tmpFile);
                    filePath = FastDfsClient.uploadFile(IOUtils.toByteArray(new FileInputStream(tmpFile)),
                            fileName, getFileSuffix(fileName));
                    fileSize = tmpFile.length();
                    break;
            }
            urlConnection.disconnect();
        } catch (Exception e) {
            if (urlConnection != null) {
                urlConnection.disconnect();
                urlConnection = null;
            }
            Exceptions.printException(e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
                urlConnection = null;
            }
        }
        SysFile sysFile = SysFile.builder().fileName(fileName).fileType(urlFile.getFileSuffix())
                .filePath(filePath).fileSize(fileSize).downLoadUrl(SysFileController.DOWNLOAD_URL_DATABASE).
                        build();
        log.debug("上传文件成功，具体信息如下： {}", sysFile.toString());
        return sysFile;
    }

    /**
     * 将本地文件上传至系统
     * @param localFile  系统临时目录的文件
     * @param directory 相对路径
     * @return 返回存储路径
     */
    public SysFile uploadFromLocal(File localFile, String directory) {
        String filePath = null;
        String fileName = localFile.getName();
        try {
            switch (serverUploadLocation) {
                case disk:
                    File targetFileDirectory = createUploadStorePath(directory);
                    Path path = Paths.get(targetFileDirectory.getPath() + ApplicationConstants.SLASH + localFile.getName());
                    Files.write(path, Files.readAllBytes(Paths.get(localFile.getAbsolutePath())));
                    filePath = path.toString();
                    break;
                case fastdfs:
                    filePath = FastDfsClient.uploadLocalFile(localFile.getAbsolutePath());
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Exceptions.printException(e);
        }
        SysFile sysFile = SysFile.builder().fileName(fileName).fileType(getFileSuffix(localFile.getAbsolutePath()))
                .filePath(filePath).fileSize(localFile.length()).downLoadUrl(SysFileController.DOWNLOAD_URL_DATABASE).
                        build();
        log.debug("上传文件成功，具体信息如下： {}", sysFile.toString());
        return sysFile;
    }

    private BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }
        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        // Copy image to buffered image
        Graphics g = bimage.createGraphics();
        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }

    /**
     * 将应用系统中的图片压缩后，再上传
     * <p>
     * 图片高质量压缩参考：http://www.lac.inpe.br/JIPCookbook/6040-howto-compressimages.jsp
     *
     * @param imageFile 应用系统Context路径下的图片
     * @param quality   压缩质量
     * @param directory 相对路径
     * @return 返回存储路径
     */
    public SysFile uploadCompressImage(File imageFile, float quality, String directory) {
        String filePath = null;
        String fileName = imageFile.getName();
        ByteArrayOutputStream baos = null;
        ImageOutputStream ios = null;
        ByteArrayInputStream bais = null;
        ImageWriter writer;
        try {
            BufferedImage image = null;
            if(getFileSuffix(imageFile.getName()).equalsIgnoreCase("bmp")){
                image = ImageIO.read(imageFile);
            } else {
//            java上传图片，压缩、更改尺寸等导致变色（表层蒙上一层红色）
//            https://blog.csdn.net/qq_25446311/article/details/79140008?tdsourcetag=s_pctim_aiomsg
                Image bufferedImage = Toolkit.getDefaultToolkit().getImage(imageFile.getAbsolutePath());
                image = this.toBufferedImage(bufferedImage);// Image to BufferedImage
            }
            Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("jpg");
            if (!writers.hasNext())
                throw new IllegalStateException("No writers found");
            writer = (ImageWriter) writers.next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);
            baos = new ByteArrayOutputStream(32768);
            switch (serverUploadLocation) {
                case disk:
                    File targetFileDirectory = createUploadStorePath(directory);
                    File compressedFile = new File(targetFileDirectory.getAbsolutePath() + ApplicationConstants.SLASH + imageFile.getName());
                    log.debug("压缩图片保存路径为 :" + compressedFile.getPath());
                    ios = ImageIO.createImageOutputStream(baos);
                    FileImageOutputStream output = new FileImageOutputStream(compressedFile);
                    writer.setOutput(output);
                    writer.write(null, new IIOImage(image, null, null), param);
                    output.flush();
                    writer.dispose();
                    ios.flush();
                    ios.close();
                    baos.close();
                    output = null;
                    writer = null;
                    ios = null;
                    baos = null;
                    filePath = compressedFile.getAbsolutePath();
                    break;
                case fastdfs:
                    ios = ImageIO.createImageOutputStream(baos);
                    File tmpFile = createTempFile(getFileSuffix(imageFile.getName()));
                    output = new FileImageOutputStream(tmpFile);
                    writer.setOutput(output);
                    writer.write(null, new IIOImage(image, null, null), param);
                    output.flush();
                    writer.dispose();
                    ios.flush();
                    ios.close();
                    baos.close();
                    output = null;
                    writer = null;
                    ios = null;
                    baos = null;
                    filePath = FastDfsClient.uploadLocalFile(tmpFile.getAbsolutePath());
                    tmpFile.deleteOnExit();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Exceptions.printException(e);
        } finally {
            if (baos != null)
                try {
                    baos.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
        }
        SysFile sysFile = SysFile.builder().fileName(fileName).fileType(getFileSuffix(imageFile.getAbsolutePath()))
                .filePath(filePath).fileSize(imageFile.length()).downLoadUrl(SysFileController.DOWNLOAD_URL_DATABASE).
                        build();
        log.debug("上传文件成功，具体信息如下： {}", sysFile.toString());
        return sysFile;
    }

    /**
     * 从远程URL上传压缩图片
     * @param imageUrl
     * @param quality
     * @param directory 相对路径
     * @return
     */
    public SysFile uploadCompressImageFromUrl(String imageUrl, float quality, String directory) {
        File imageFile = downloadFromUrl(imageUrl);
        return uploadCompressImage(imageFile, quality, directory);
    }

    public File createUploadStorePath(String directory) throws IOException {
        String storePath = config.getUploadPath()
                + ApplicationConstants.SLASH + directory
                + ApplicationConstants.SLASH + DateUtil.getCurrentStr();
                //+ ApplicationConstants.SLASH + CodeGenerator.randomInt(4);
        File targetFileDirectory = new File(storePath);
        if (!targetFileDirectory.exists()) {
            FileUtils.forceMkdir(targetFileDirectory);
            log.debug("Directory {} is not exist, force create direcorty....", storePath);
        }
        return targetFileDirectory;
    }

    /**
     * 创建临时后缀临时文件
     * @return
     */
    public static File createTempFile(){
        return createTempFile(CodeGenerator.randomChar(4));
    }

    /**
     * 创建带后缀临时文件
     * @param suffix
     * @return
     */
    public static File createTempFile(String suffix){
        File tempFile = null;
        try {
            tempFile = File.createTempFile(CodeGenerator.randomChar(4), ApplicationConstants.DOT + suffix);
        } catch (IOException e) {
            Exceptions.printException(e);
        }
        log.debug("创建的临时文件路径为：{}", tempFile.getAbsolutePath());
        return tempFile;
    }

    /**
     * 从系统中下载文件
     * @param filePath
     * @return
     */
    public File getFileFromSystem(String filePath){
        File realFile = null;
        log.debug("Want to get file {} from {}", filePath, serverUploadLocation);
        switch (serverUploadLocation) {
            case disk:
                realFile = new File(filePath);
                break;
            case fastdfs:
                realFile = downloadFromUrl(getFileUrlFromFastDfs(filePath));
                break;
        }
        return realFile;
    }

    /**
     * 获取保存在FastDfs中的文件访问路径
     * @param filePath
     * @return
     */
    public String getFileUrlFromFastDfs(String filePath){
        return config.getAppHostPort() + ApplicationConstants.SLASH + filePath;
    }

    /**
     * 根据远程文件的url下载文件
     * @param fileUrl
     * @return
     */
    public File downloadFromUrl(String fileUrl) {
        File targetFile = null;
        HttpURLConnection urlConnection = null;
        try {
            UrlFile urlFile = openAndConnectUrl(fileUrl);
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
            if (urlConnection!= null) {
                urlConnection.disconnect();
                urlConnection = null;
            }
            Exceptions.printException(e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return targetFile;
    }

    private UrlFile openAndConnectUrl(String remoteFileUrl) throws Exception {
        String urlStr = FilenameUtils.getFullPath(remoteFileUrl);
        //URL路径中有/分割的文件名，进行URL编码处理中文，否则不做处理
        String fileName = getFileName(remoteFileUrl);
        if(StringUtils.isNotEmpty(fileName)){
            if(ChineseHelper.containsChinese(fileName)){
                urlStr += URLEncoder.encode(fileName);
            }else {
                urlStr += fileName;
            }
        }
        log.debug("即将从地址【{}】下载文件", urlStr);
        URL connUrl = new URL(urlStr);
        HttpURLConnection urlConnection = (HttpURLConnection) connUrl.openConnection();
        urlConnection.setDoInput(true);
        urlConnection.setRequestMethod(ApplicationConstants.HTTPGET);
        urlConnection.connect();
        String suffix = getFileSuffix(remoteFileUrl);
        if(StringUtils.isEmpty(suffix)){
            BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
            String contentType = HttpURLConnection.guessContentTypeFromStream(bis);
            if(contentType.trim().startsWith("image")){
                suffix = StringUtils.substringAfter(contentType, ApplicationConstants.SLASH);
            }
        }
        return UrlFile.builder().remoteFileUrl(remoteFileUrl).conn(urlConnection).connUrl(connUrl).fileName(fileName).fileSuffix(suffix).build();
    }

    public int deleteFile(String filePath) {
        int result = 0;
        log.debug("Will remove file at {}, and filePath is {}", serverUploadLocation, filePath);
        if (StringUtils.isNotEmpty(filePath)) {
            switch (serverUploadLocation) {
                case disk:
                    try {
                        FileUtils.forceDelete(new File(filePath));
                    } catch (Exception e) {
                        result = -1;
                        Exceptions.printException(e);
                    }
                    break;
                case fastdfs:
                    try {
                        FastDfsClient.deleteFile(filePath);
                    } catch (Exception e) {
                        result = -1;
                        Exceptions.printException(e);
                    }
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    public static void setServerUploadLocation(StoreLocation serverUploadLocation) {
        AppFileUtil.serverUploadLocation = serverUploadLocation;
    }

    /**
     * 读取放置在项目resource目录下的文件，以二进制流的方式方法，
     *  因为项目打包后在读取项目里面的文件必须用流的方式获取，否则用其他方式获取会提示找不到文件
     * @param filePath
     * @return
     */
    public static byte[] getFileByte(String filePath){
        byte[] fileByte = null;
        try {
            if ( StringUtils.isEmpty( filePath ) ){
                return null;
            }
            ClassPathResource classPathResource = new ClassPathResource( filePath );
            BufferedInputStream bufferedInputStream = new BufferedInputStream( classPathResource.getInputStream() );
            fileByte = new byte[bufferedInputStream.available()];
            bufferedInputStream.read(fileByte);
            bufferedInputStream.close();
        } catch ( IOException e ) {
            Exceptions.printException(e);
        }
        return fileByte;
    }

    public static void main(String[] args) throws Exception {
//        String fileUrl = "http://mmbiz.qpic.cn/mmbiz_jpg/MyDnHITZqkiaoqpMdyFh84RP6pDZ4dMIHa2d4JFJWO5R6nGPVN1EA9GyVnfqiaxZ9EY5L3L0CBpAvRheQlxgvJ5Q/0";
//        String fileUrl = "http://10.92.81.163:8088/group1/M00/00/00/ClxQR1uWKAyAM4c4AAAyAsyPzjY83.docx";
//        String fileUrl = "http://shmhzs.free.idcfengye.com/anon/file/anonymous/sys/file/download?id=V569213798079004672";
        String fileUrl = "http://10.87.42.136:8088/maipdocument/夏季防洪防汛.doc";
        AppFileUtil util = new AppFileUtil();
        File files = util.downloadFromUrl(fileUrl);
        System.out.println(getFileName(fileUrl));
        System.out.println(getFileBaseName(fileUrl));
        System.out.println(getFileSuffix(fileUrl));
    }
}