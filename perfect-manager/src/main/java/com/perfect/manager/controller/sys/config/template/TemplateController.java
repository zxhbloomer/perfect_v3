package com.perfect.manager.controller.sys.config.template;

import com.perfect.bean.vo.sys.config.module.SModuleVo;
import com.perfect.common.annotation.SysLog;
import com.perfect.core.service.sys.config.module.IModuleService;
import com.perfect.excel.bean.importconfig.template.ExcelTemplate;
import com.perfect.excel.export.ExcelUtil;
import com.perfect.framework.base.controller.v1.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public void info(@RequestParam("id") String id, HttpServletResponse response) throws IOException {

        SModuleVo vo = service.getTemplateBeanByModuleName(id);
        String excelName = vo.getTemplateName();
        String jsonConfig = vo.getTemplateContext();
        ExcelTemplate et = initExcelTemplate(jsonConfig);
        ExcelUtil util = new ExcelUtil();
        util.exportExcelHead("字典类型模版文件", "模板表格", et, response);
    }

}
