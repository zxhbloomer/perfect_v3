package com.perfect.manager.controller.sys.config.tentant;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.perfect.bean.entity.sys.config.tenant.STentantEntity;
import com.perfect.bean.pojo.result.JsonResult;
import com.perfect.bean.result.utils.v1.ResultUtil;
import com.perfect.bean.utils.common.tree.TreeUtil;
import com.perfect.bean.vo.sys.config.config.SConfigVo;
import com.perfect.bean.vo.sys.config.tenant.STentantTreeVo;
import com.perfect.bean.vo.sys.config.tenant.STentantVo;
import com.perfect.common.annotation.RepeatSubmit;
import com.perfect.common.annotation.SysLog;
import com.perfect.common.exception.InsertErrorException;
import com.perfect.common.exception.UpdateErrorException;
import com.perfect.core.service.sys.config.tentant.ITentantService;
import com.perfect.framework.base.controller.v1.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author zhangxh
 */
@RestController
@RequestMapping(value = "/api/v1/tentant")
@Slf4j
@Api("租户相关")
public class TentantController extends BaseController {

    @Autowired
    private ITentantService service;

    @Autowired
    private RestTemplate restTemplate;

    @SysLog("根据查询条件，获取租户信息")
    @ApiOperation("获取租户树数据")
    @PostMapping("/tree/list")
    @ResponseBody
    public ResponseEntity<JsonResult<List<STentantTreeVo>>> treeList(@RequestBody(required = false)
        SConfigVo searchCondition) throws IllegalAccessException, InstantiationException {
        List<STentantTreeVo> vo = service.getTreeList(null,null);
        List<STentantTreeVo> rtnVo = TreeUtil.getTreeList(vo);
        return ResponseEntity.ok().body(ResultUtil.OK(rtnVo));
    }

    @SysLog("根据查询条件，获取租户信息")
    @ApiOperation("获取级联数据")
    @PostMapping("/cascader/list")
    @ResponseBody
    public ResponseEntity<JsonResult<List<STentantTreeVo>>> cascaderList(@RequestBody(required = false)
        SConfigVo searchCondition) throws IllegalAccessException, InstantiationException {
        List<STentantTreeVo> vo = service.getCascaderList(null,null);
        List<STentantTreeVo> rtnVo = TreeUtil.getTreeList(vo);
        return ResponseEntity.ok().body(ResultUtil.OK(rtnVo,true));
    }

//    @SysLog("根据查询条件，获取租户信息")
//    @ApiOperation("获取租户树数据")
//    @PostMapping("/grid/list")
//    @ResponseBody
//    public ResponseEntity<JsonResult<List<STentantTreeVo>>> gridList(@RequestBody(required = false)
//        SConfigVo searchCondition) throws IllegalAccessException, InstantiationException {
//        List<STentantTreeVo> vo = service.getTreeList(Long.valueOf(1),"根");
//        List<STentantTreeVo> rtnVo = TreeUtil.getTreeList(vo);
//        return ResponseEntity.ok().body(ResultUtil.OK(rtnVo));
//    }

    @SysLog("根据参数id，获取系统参数信息")
    @ApiOperation("根据参数id，获取系统参数信息")
    @PostMapping("{ id }")
    @ResponseBody
    public ResponseEntity<JsonResult<STentantEntity>> info(@RequestParam("id") String id) {

        STentantEntity entity = service.getById(id);

//        ResponseEntity<OAuth2AccessToken
        return ResponseEntity.ok().body(ResultUtil.OK(entity));
    }

    @SysLog("根据查询条件，获取系统参数信息")
    @ApiOperation("根据参数id，获取系统参数信息")
    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<JsonResult<IPage<STentantVo>>> list(@RequestBody(required = false)
        STentantVo searchCondition) throws IllegalAccessException, InstantiationException {
        IPage<STentantVo> entity = service.selectPage(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.OK(entity));
    }

    @SysLog("系统参数数据更新保存")
    @ApiOperation("根据参数id，获取系统参数信息")
    @PostMapping("/save")
    @ResponseBody
    @RepeatSubmit
    public ResponseEntity<JsonResult<STentantEntity>> save(@RequestBody(required = false) STentantEntity bean) {

        if(service.update(bean).isSuccess()){
            return ResponseEntity.ok().body(ResultUtil.OK(service.getById(bean.getId()),"更新成功"));
        } else {
            throw new UpdateErrorException("保存的数据已经被修改，请查询后重新编辑更新。");
        }
    }

    @SysLog("系统参数数据新增保存")
    @ApiOperation("根据参数id，获取系统参数信息")
    @PostMapping("/insert")
    @ResponseBody
    @RepeatSubmit
    public ResponseEntity<JsonResult<STentantVo>> insert(@RequestBody(required = false) STentantEntity bean) {
        // 默认启用
        bean.setIsenable(true);
        if(service.insert(bean).isSuccess()){
            return ResponseEntity.ok().body(ResultUtil.OK(service.selectByid(bean.getId()),"插入成功"));
        } else {
            throw new InsertErrorException("新增保存失败。");
        }
    }

//    @SysLog("系统参数数据导出")
//    @ApiOperation("根据选择的数据，系统参数数据导出")
//    @PostMapping("/export_all")
//    public void exportAll(@RequestBody(required = false) STentantTreeVo searchCondition, HttpServletResponse response)
//        throws IllegalAccessException, InstantiationException, IOException {
//        List<STentantTreeVo> searchResult = service.select(searchCondition);
//        List<SConfigDataExportVo> rtnList = BeanUtilsSupport.copyProperties(searchResult, SConfigDataExportVo.class);
//        ExcelUtil<SConfigDataExportVo> util = new ExcelUtil<>(SConfigDataExportVo.class);
//        util.exportExcel("系统参数数据导出", "系统参数数据", rtnList, response);
//    }
//
//    @SysLog("系统参数数据导出")
//    @ApiOperation("根据选择的数据，系统参数数据导出")
//    @PostMapping("/export_selection")
//    public void exportSelection(@RequestBody(required = false) List<STentantTreeVo> searchConditionList,
//        HttpServletResponse response)
//        throws IllegalAccessException, InstantiationException, IOException {
//        List<STentantTreeVo> searchResult = service.selectIdsIn(searchConditionList);
//        List<SResourceExportVo> rtnList = BeanUtilsSupport.copyProperties(searchResult, SConfigDataExportVo.class);
//        ExcelUtil<SResourceExportVo> util = new ExcelUtil<>(SResourceExportVo.class);
//        util.exportExcel("系统参数数据导出", "系统参数数据", rtnList, response);
//    }
}
