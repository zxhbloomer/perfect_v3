package com.perfect.manager.controller.sys.config.dict;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.perfect.bean.entity.sys.config.dict.SDictTypeEntity;
import com.perfect.bean.vo.sys.config.dict.SDictTypeExportVo;
import com.perfect.bean.vo.sys.config.dict.SDictTypeVo;
import com.perfect.core.service.sys.config.dict.ISDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.perfect.bean.entity.sys.config.resource.SResourceEntity;
import com.perfect.bean.pojo.JSONResult;
import com.perfect.bean.result.v1.ResultUtil;
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
@RequestMapping(value = "/api/v1/dict")
@Slf4j
@Api("字典主表相关")
public class DictTypeController extends BaseController {

    @Autowired
    private ISDictTypeService isDictTypeService;

    @Autowired
    private RestTemplate restTemplate;

    @SysLog("根据参数id，获取字典主表信息")
    @ApiOperation("根据参数id，获取字典主表信息")
    @PostMapping("{ id }")
    @ResponseBody
    public ResponseEntity<JSONResult<SDictTypeEntity>> info(@RequestParam("id") String id) {

        SDictTypeEntity entity = isDictTypeService.getById(id);

//        ResponseEntity<OAuth2AccessToken
        return ResponseEntity.ok().body(ResultUtil.success(entity));
    }

    @SysLog("根据查询条件，获取字典主表信息")
    @ApiOperation("根据参数id，获取字典主表信息")
    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<JSONResult<IPage<SDictTypeEntity>>> list(@RequestBody(required = false)
        SDictTypeVo searchCondition) throws IllegalAccessException, InstantiationException {
        IPage<SDictTypeEntity> entity = isDictTypeService.selectPage(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.success(entity));
    }

    @SysLog("字典主表数据更新保存")
    @ApiOperation("根据参数id，获取字典主表信息")
    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<JSONResult<SDictTypeEntity>> save(@RequestBody(required = false) SDictTypeEntity bean) {
        if(isDictTypeService.updateById(bean)){
            return ResponseEntity.ok().body(ResultUtil.success(isDictTypeService.getById(bean.getId()),"更新成功"));
        } else {
            throw new UpdateErrorException("保存的数据已经被修改，请查询后重新编辑更新。");
        }
    }

    @SysLog("字典主表数据新增保存")
    @ApiOperation("根据参数id，获取字典主表信息")
    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<JSONResult<SDictTypeEntity>> insert(@RequestBody(required = false) SDictTypeEntity bean) {
        if(isDictTypeService.save(bean)){
            return ResponseEntity.ok().body(ResultUtil.success(isDictTypeService.getById(bean.getId()),"插入成功"));
        } else {
            throw new InsertErrorException("新增保存失败。");
        }
    }

    @SysLog("字典主表数据导出")
    @ApiOperation("根据选择的数据，字典主表数据导出")
    @PostMapping("/export_all")
    public void exportAll(@RequestBody(required = false) SDictTypeVo searchCondition, HttpServletResponse response)
        throws IllegalAccessException, InstantiationException, IOException {
        // List<SRoleExportVo> rtnList = new ArrayList<>();
        List<SDictTypeEntity> searchResult = isDictTypeService.select(searchCondition);
        List<SDictTypeExportVo> rtnList = BeanUtilsSupport.copyProperties(searchResult, SRoleExportVo.class);
        ExcelUtil<SDictTypeExportVo> util = new ExcelUtil<>(SDictTypeExportVo.class);
        util.exportExcel("字典主表数据导出", "字典主表数据", rtnList, response);
    }

    @SysLog("字典主表数据导出")
    @ApiOperation("根据选择的数据，字典主表数据导出")
    @PostMapping("/export_selection")
    public void exportSelection(@RequestBody(required = false) List<SDictTypeVo> searchConditionList,
        HttpServletResponse response)
        throws IllegalAccessException, InstantiationException, IOException {
        List<SDictTypeEntity> searchResult = isDictTypeService.selectIdsIn(searchConditionList);
        List<SResourceExportVo> rtnList = BeanUtilsSupport.copyProperties(searchResult, SRoleExportVo.class);
        ExcelUtil<SResourceExportVo> util = new ExcelUtil<>(SResourceExportVo.class);
        util.exportExcel("字典主表数据导出", "字典主表数据", rtnList, response);
    }

    @SysLog("字典主表数据逻辑删除复原")
    @ApiOperation("根据参数id，逻辑删除复原数据")
    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<JSONResult<String>> delete(@RequestBody(required = false) List<SDictTypeVo> searchConditionList) {
        isDictTypeService.deleteByIdsIn(searchConditionList);
        return ResponseEntity.ok().body(ResultUtil.success("OK"));
    }
}
