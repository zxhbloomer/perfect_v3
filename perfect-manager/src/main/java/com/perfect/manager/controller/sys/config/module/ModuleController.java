package com.perfect.manager.controller.sys.config.module;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.perfect.bean.entity.sys.config.module.SModuleEntity;
import com.perfect.bean.vo.sys.config.module.SModuleExportVo;
import com.perfect.bean.vo.sys.config.module.SModuleVo;
import com.perfect.core.service.sys.config.module.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.perfect.bean.pojo.result.JsonResult;
import com.perfect.bean.result.utils.v1.ResultUtil;
import com.perfect.common.annotation.SysLog;
import com.perfect.common.exception.InsertErrorException;
import com.perfect.common.exception.UpdateErrorException;
import com.perfect.common.utils.bean.BeanUtilsSupport;
import com.perfect.excel.export.ExcelUtil;
import com.perfect.framework.base.controller.v1.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangxh
 */
@RestController
@RequestMapping(value = "/api/v1/module")
@Slf4j
@Api("模块相关")
public class ModuleController extends BaseController {

    @Autowired
    private IModuleService service;

    @Autowired
    private RestTemplate restTemplate;

    @SysLog("根据参数id，获取资源表信息")
    @ApiOperation("根据参数id，获取资源表信息")
    @PostMapping("{ id }")
    @ResponseBody
    public ResponseEntity<JsonResult<SModuleVo>> info(@RequestParam("id") Long id) {

        SModuleVo entity = service.selectByid(id);

//        ResponseEntity<OAuth2AccessToken
        return ResponseEntity.ok().body(ResultUtil.OK(entity));
    }

    @SysLog("根据查询条件，获取资源表信息")
    @ApiOperation("根据参数id，获取资源表信息")
    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<JsonResult<IPage<SModuleVo>>> list(@RequestBody(required = false)
        SModuleVo searchCondition)
        throws InstantiationException, IllegalAccessException {
        IPage<SModuleVo> vo = service.selectPage(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.OK(vo));
    }

    @SysLog("资源表数据更新保存")
    @ApiOperation("根据参数id，获取资源表信息")
    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<JsonResult<SModuleVo>> save(@RequestBody(required = false) SModuleEntity bean) {
        if(service.update(bean).isSuccess()){
            return ResponseEntity.ok().body(ResultUtil.OK(service.selectByid(bean.getId()),"更新成功"));
        } else {
            throw new UpdateErrorException("保存的数据已经被修改，请查询后重新编辑更新。");
        }
    }

    @SysLog("资源表数据新增保存")
    @ApiOperation("根据参数id，获取资源表信息")
    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<JsonResult<SModuleVo>> insert(@RequestBody(required = false) SModuleEntity bean) {
        if(service.insert(bean).isSuccess()){
            return ResponseEntity.ok().body(ResultUtil.OK(service.selectByid(bean.getId()),"插入成功"));
        } else {
            throw new InsertErrorException("新增保存失败。");
        }
    }

    @SysLog("资源表数据导出")
    @ApiOperation("根据选择的数据，资源表数据导出")
    @PostMapping("/export_all")
    public void exportAll(@RequestBody(required = false) SModuleVo searchCondition, HttpServletResponse response)
        throws IllegalAccessException, InstantiationException, IOException {
        List<SModuleEntity> searchResult = service.select(searchCondition);
        List<SModuleExportVo> rtnList = BeanUtilsSupport.copyProperties(searchResult, SModuleExportVo.class);
        ExcelUtil<SModuleExportVo> util = new ExcelUtil<>(SModuleExportVo.class);
        util.exportExcel("资源表数据导出", "资源表数据", rtnList, response);
    }

    @SysLog("资源数据导出")
    @ApiOperation("根据选择的数据，资源数据导出")
    @PostMapping("/export_selection")
    public void exportSelection(@RequestBody(required = false) List<SModuleVo> searchConditionList,
        HttpServletResponse response)
        throws IllegalAccessException, InstantiationException, IOException {
        List<SModuleEntity> searchResult = service.selectIdsIn(searchConditionList);
        List<SModuleExportVo> rtnList = BeanUtilsSupport.copyProperties(searchResult, SModuleExportVo.class);
        ExcelUtil<SModuleExportVo> util = new ExcelUtil<>(SModuleExportVo.class);
        util.exportExcel("资源数据导出", "资源数据", rtnList, response);
    }

    @SysLog("资源数据逻辑删除复原")
    @ApiOperation("根据参数id，逻辑删除复原数据")
    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<JsonResult<String>> delete(@RequestBody(required = false) List<SModuleVo> searchConditionList) {
        service.deleteByIdsIn(searchConditionList);
        return ResponseEntity.ok().body(ResultUtil.OK("OK"));
    }
}
