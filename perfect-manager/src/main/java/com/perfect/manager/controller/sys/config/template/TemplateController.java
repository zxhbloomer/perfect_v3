package com.perfect.manager.controller.sys.config.template;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.perfect.bean.vo.sys.config.dict.SDictTypeExportVo;
import com.perfect.bean.vo.sys.config.module.SModuleVo;
import com.perfect.core.service.sys.config.module.IModuleService;
import com.perfect.excel.bean.importconfig.template.ExcelTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.perfect.bean.entity.sys.config.resource.SResourceEntity;
import com.perfect.bean.pojo.result.JsonResult;
import com.perfect.bean.result.utils.v1.ResultUtil;
import com.perfect.bean.vo.sys.config.resource.SResourceExportVo;
import com.perfect.bean.vo.sys.config.resource.SResourceVo;
import com.perfect.bean.vo.sys.rabc.role.SRoleExportVo;
import com.perfect.common.annotation.SysLog;
import com.perfect.common.exception.InsertErrorException;
import com.perfect.common.exception.UpdateErrorException;
import com.perfect.common.utils.bean.BeanUtilsSupport;
import com.perfect.core.service.sys.config.resource.ISResourceService;
import com.perfect.excel.export.ExcelUtil;
import com.perfect.framework.base.controller.v1.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangxh
 */
@RestController
@RequestMapping(value = "/api/v1/template.html")
@Slf4j
@Api("excel模板相关")
public class TemplateController extends BaseController {

    @Autowired
    private IModuleService service;

    @Autowired
    private RestTemplate restTemplate;

    @SysLog("excel上传模板下载")
    @ApiOperation("根据页面id，获取相应的下载模板")
    @GetMapping
    @ResponseBody
    public void info(@RequestParam("id") String id, HttpServletResponse response) {

        SModuleVo vo = service.getTemplateBeanByModuleName(id);
        String excelName = vo.getTemplateName();
        String jsonConfig = vo.getTemplateContext();
        ExcelTemplate et = initExcelTemplate(jsonConfig);
        ExcelUtil util = new ExcelUtil();
//        util.exportExcelHead(excelName, "字典主表数据", "模板表格", et, response);
    }

}
