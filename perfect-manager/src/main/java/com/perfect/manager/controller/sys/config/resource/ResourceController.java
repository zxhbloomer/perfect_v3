package com.perfect.manager.controller.sys.config.resource;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.perfect.bean.entity.sys.config.resource.SResourceEntity;
import com.perfect.bean.pojo.result.JsonResult;
import com.perfect.bean.result.utils.v1.ResultUtil;
import com.perfect.bean.vo.sys.rabc.role.SRoleExportVo;
import com.perfect.bean.vo.sys.config.resource.SResourceExportVo;
import com.perfect.bean.vo.sys.config.resource.SResourceVo;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author zhangxh
 */
@RestController
@RequestMapping(value = "/api/v1/resource")
@Slf4j
@Api("资源表相关")
public class ResourceController extends BaseController {

    @Autowired
    private ISResourceService isResourceService;

    @Autowired
    private RestTemplate restTemplate;

    @SysLog("根据参数id，获取资源表信息")
    @ApiOperation("根据参数id，获取资源表信息")
    @PostMapping("{ id }")
    @ResponseBody
    public ResponseEntity<JsonResult<SResourceEntity>> info(@RequestParam("id") String id) {

        SResourceEntity sResourceEntity = isResourceService.getById(id);

//        ResponseEntity<OAuth2AccessToken
        return ResponseEntity.ok().body(ResultUtil.OK(sResourceEntity));
    }

    @SysLog("根据查询条件，获取资源表信息")
    @ApiOperation("根据参数id，获取资源表信息")
    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<JsonResult<IPage<SResourceEntity>>> list(@RequestBody(required = false)
        SResourceVo searchCondition)
        throws InstantiationException, IllegalAccessException {
        IPage<SResourceEntity> sResourceEntity = isResourceService.selectPage(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.OK(sResourceEntity));
    }

    @SysLog("资源表数据更新保存")
    @ApiOperation("根据参数id，获取资源表信息")
    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<JsonResult<SResourceEntity>> save(@RequestBody(required = false) SResourceEntity sResourceEntity) {
        if(isResourceService.updateById(sResourceEntity)){
            return ResponseEntity.ok().body(ResultUtil.OK(isResourceService.getById(sResourceEntity.getId()),"更新成功"));
        } else {
            throw new UpdateErrorException("保存的数据已经被修改，请查询后重新编辑更新。");
        }
    }

    @SysLog("资源表数据新增保存")
    @ApiOperation("根据参数id，获取资源表信息")
    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<JsonResult<SResourceEntity>> insert(@RequestBody(required = false) SResourceEntity sResourceEntity) {
        if(isResourceService.save(sResourceEntity)){
            return ResponseEntity.ok().body(ResultUtil.OK(isResourceService.getById(sResourceEntity.getId()),"插入成功"));
        } else {
            throw new InsertErrorException("新增保存失败。");
        }
    }

    @SysLog("资源表数据导出")
    @ApiOperation("根据选择的数据，资源表数据导出")
    @PostMapping("/export_all")
    public void exportAll(@RequestBody(required = false) SResourceVo searchCondition, HttpServletResponse response)
        throws IllegalAccessException, InstantiationException, IOException {
        // List<SRoleExportVo> rtnList = new ArrayList<>();
        List<SResourceEntity> searchResult = isResourceService.select(searchCondition);
        List<SResourceExportVo> rtnList = BeanUtilsSupport.copyProperties(searchResult, SRoleExportVo.class);
        ExcelUtil<SResourceExportVo> util = new ExcelUtil<>(SResourceExportVo.class);
        util.exportExcel("资源表数据导出", "资源表数据", rtnList, response);
    }

    @SysLog("资源数据导出")
    @ApiOperation("根据选择的数据，资源数据导出")
    @PostMapping("/export_selection")
    public void exportSelection(@RequestBody(required = false) List<SResourceVo> searchConditionList,
        HttpServletResponse response)
        throws IllegalAccessException, InstantiationException, IOException {
        List<SResourceEntity> searchResult = isResourceService.selectIdsIn(searchConditionList);
        List<SResourceExportVo> rtnList = BeanUtilsSupport.copyProperties(searchResult, SRoleExportVo.class);
        ExcelUtil<SResourceExportVo> util = new ExcelUtil<>(SResourceExportVo.class);
        util.exportExcel("资源数据导出", "资源数据", rtnList, response);
    }

    @SysLog("资源数据逻辑删除复原")
    @ApiOperation("根据参数id，逻辑删除复原数据")
    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<JsonResult<String>> delete(@RequestBody(required = false) List<SResourceVo> searchConditionList) {
        isResourceService.deleteByIdsIn(searchConditionList);
        return ResponseEntity.ok().body(ResultUtil.OK("OK"));
    }
}
