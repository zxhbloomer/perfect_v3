package com.perfect.manager.controller.sys.rabc;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.perfect.bean.entity.system.rabc.SRoleEntity;
import com.perfect.bean.pojo.JSONResult;
import com.perfect.bean.result.v1.ResultUtil;
import com.perfect.bean.vo.sys.rabc.role.SRoleExportVo;
import com.perfect.bean.vo.sys.rabc.role.SysRoleVo;
import com.perfect.common.annotation.SysLog;
import com.perfect.common.base.controller.v1.BaseController;
import com.perfect.common.exception.InsertErrorException;
import com.perfect.common.exception.UpdateErrorException;
import com.perfect.common.utils.bean.BeanUtilsSupport;
import com.perfect.core.service.system.rabc.ISRoleService;
import com.perfect.excel.bean.importconfig.template.ExcelTemplate;
import com.perfect.excel.conf.validator.RowValidateResult;
import com.perfect.excel.export.ExcelUtil;
import com.perfect.excel.upload.PerfectExcelReader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author zhangxh
 */
@RestController
@RequestMapping(value = "/api/v1/role")
@Slf4j
@Api("角色相关")
public class RoleController extends BaseController {

    @Autowired
    private ISRoleService isRoleService;

    @Autowired
    private RestTemplate restTemplate;

    @SysLog("根据参数id，获取角色信息")
    @ApiOperation("根据参数id，获取角色信息")
    @PostMapping("{ id }")
    @ResponseBody
    public ResponseEntity<JSONResult<SRoleEntity>> info(@RequestParam("id") String id) {

        SRoleEntity sRoleEntity = isRoleService.getById(id);

//        ResponseEntity<OAuth2AccessToken
        return ResponseEntity.ok().body(ResultUtil.success(sRoleEntity));
    }

    @SysLog("根据查询条件，获取角色信息")
    @ApiOperation("根据参数id，获取角色信息")
    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<JSONResult<IPage<SRoleEntity>>> list(@RequestBody(required = false) SysRoleVo searchCondition)
        throws InstantiationException, IllegalAccessException {
        IPage<SRoleEntity> sRoleEntity = isRoleService.selectPage(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.success(sRoleEntity));
    }

    @SysLog("角色数据更新保存")
    @ApiOperation("根据参数id，获取角色信息")
    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<JSONResult<SRoleEntity>> save(@RequestBody(required = false) SRoleEntity sRoleEntity) {
        if(isRoleService.updateById(sRoleEntity)){
            return ResponseEntity.ok().body(ResultUtil.success(isRoleService.getById(sRoleEntity.getId()),"更新成功"));
        } else {
            throw new UpdateErrorException("保存的数据已经被修改，请查询后重新编辑更新。");
        }
    }

    @SysLog("角色数据新增保存")
    @ApiOperation("根据参数id，获取角色信息")
    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<JSONResult<SRoleEntity>> insert(@RequestBody(required = false) SRoleEntity sRoleEntity) {
        if(isRoleService.save(sRoleEntity)){
            return ResponseEntity.ok().body(ResultUtil.success(isRoleService.getById(sRoleEntity.getId()),"插入成功"));
        } else {
            throw new InsertErrorException("新增保存失败。");
        }
    }

    @SysLog("角色数据导出")
    @ApiOperation("根据选择的数据，角色数据导出")
    @PostMapping("/export_all")
    public void exportAll(@RequestBody(required = false) SysRoleVo searchCondition, HttpServletResponse response)
        throws IllegalAccessException, InstantiationException, IOException {
        // List<SRoleExportVo> rtnList = new ArrayList<>();
        List<SRoleEntity> searchResult = isRoleService.select(searchCondition);
        List<SRoleExportVo> rtnList = BeanUtilsSupport.copyProperties(searchResult, SRoleExportVo.class);
        ExcelUtil<SRoleExportVo> util = new ExcelUtil<>(SRoleExportVo.class);
        util.exportExcel("角色数据导出", "角色数据", rtnList, response);
    }

    @SysLog("角色数据导出")
    @ApiOperation("根据选择的数据，角色数据导出")
    @PostMapping("/export_selection")
    public void exportSelection(@RequestBody(required = false) List<SysRoleVo> searchConditionList,
        HttpServletResponse response)
        throws IllegalAccessException, InstantiationException, IOException {
        List<SRoleEntity> searchResult = isRoleService.selectIdsIn(searchConditionList);
        List<SRoleExportVo> rtnList = BeanUtilsSupport.copyProperties(searchResult, SRoleExportVo.class);
        ExcelUtil<SRoleExportVo> util = new ExcelUtil<>(SRoleExportVo.class);
        util.exportExcel("角色数据导出", "角色数据", rtnList, response);
    }

    @SysLog("角色数据导入")
    @ApiOperation("角色数据模板导入")
    @PostMapping("/import")
    public void importData(@RequestBody(required = false) SysRoleVo uploadData,
        HttpServletResponse response) throws Exception {

        // file bean 保存数据库

        // 文件下载到流
        String fileUrl = uploadData.getFsType2Url();
        ResponseEntity<byte[]> rtnResponse = restTemplate.getForEntity(fileUrl, byte[].class);
        InputStream is =  new ByteArrayInputStream(rtnResponse.getBody());

        // 文件分析，判断是否是excel文档
        if (FileMagic.valueOf(is) == FileMagic.OLE2){
            // Office 2003 ，xls
        } else if (FileMagic.valueOf(is) == FileMagic.OOXML) {
            // Office 2007 +，xlsx
        } else {
            // 非excel文档，报错
            throw new IllegalArgumentException("导入的文件不是office excel，请选择正确的文件来进行上传");
        }
        // 文件导入
        // 1、获取模板配置类
        String json = "{\"dataRows\":{\"dataCols\":[{\"index\":0,\"name\":\"type\"},{\"convertor\":\"date\",\"index\":1,\"listValiDator\":[{\"validtorName\":\"required\"},{\"param\":[{\"name\":\"dateFormat\",\"value\":\"yyyy-MM-dd HH:mm:ss\"}],\"validtorName\":\"datetime\"}],\"name\":\"code\"},{\"index\":2,\"name\":\"name\"},{\"index\":3,\"name\":\"descr\"},{\"index\":4,\"name\":\"simpleName\"}]},\"titleRows\":[{\"cols\":[{\"colSpan\":1,\"title\":\"角色类型\"},{\"colSpan\":1,\"title\":\"角色编码\"},{\"colSpan\":1,\"title\":\"角色名称\"},{\"colSpan\":1,\"title\":\"描述\"},{\"colSpan\":1,\"title\":\"简称\"}]}]}";
        ExcelTemplate et = JSON.parseObject(json, ExcelTemplate.class);
        // 初始化
        et.initValidator();
        PerfectExcelReader reader = new PerfectExcelReader(is);
        reader.setExcelTemplate(et);
        List<SRoleEntity> beans = reader.readBeans(SRoleEntity.class);
        List<RowValidateResult> rowValidateResults = reader.getRowValidateResults();

        reader.getValidateResultsInFIle();

        is.close();
        System.out.println("uploadData");
    }
}
