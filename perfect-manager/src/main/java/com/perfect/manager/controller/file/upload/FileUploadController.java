package com.perfect.manager.controller.file.upload;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.perfect.bean.pojo.JSONResult;
import com.perfect.bean.pojo.fs.UploadFileResultPojo;
import com.perfect.common.annotation.SysLog;
import com.perfect.common.base.controller.v1.BaseController;
import com.perfect.common.utils.fs.FsDownloadAndUploadUtil;
import com.perfect.common.utils.result.ResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件上传
 *
 * @author zxh
 * @date 2019年 07月25日 22:06:28
 */
@RestController
@RequestMapping(value = "/api/v1/file")
@Slf4j
@Api("文件上传相关")
public class FileUploadController extends BaseController {

    @SysLog("文件上传")
    @ApiOperation("文件上传")
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<JSONResult<UploadFileResultPojo>> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        UploadFileResultPojo uploadFileResultPojo = FsDownloadAndUploadUtil.uploadFile(multipartFile);
        return ResponseEntity.ok().body(ResultUtil.success(uploadFileResultPojo));
    }
}
