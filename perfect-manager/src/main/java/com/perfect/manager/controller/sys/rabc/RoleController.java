package com.perfect.manager.controller.sys.rabc;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.perfect.bean.entity.system.rabc.SRoleEntity;
import com.perfect.bean.pojo.JSONResult;
import com.perfect.bean.vo.sys.rabc.role.SysRoleVo;
import com.perfect.common.annotation.SysLog;
import com.perfect.common.base.controller.v1.BaseController;
import com.perfect.common.utils.result.ResultUtil;
import com.perfect.core.service.system.rabc.ISRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/role")
@Slf4j
@Api("角色相关")
public class RoleController extends BaseController {

    @Autowired
    private ISRoleService isRoleService;

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
    public ResponseEntity<JSONResult<IPage<SRoleEntity>>> list(@RequestBody(required = false) SysRoleVo searchCondition) {
        IPage<SRoleEntity> sRoleEntity = isRoleService.page(
                new Page(searchCondition.getPageCondition().getCurrent(), searchCondition.getPageCondition().getSize())
        );
        return ResponseEntity.ok().body(ResultUtil.success(sRoleEntity));
    }

    @SysLog("角色数据保存")
    @ApiOperation("根据参数id，获取角色信息")
    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<JSONResult<String>> save(@RequestBody(required = false) SRoleEntity sRoleEntity) {
        isRoleService.saveOrUpdate(sRoleEntity);
        return ResponseEntity.ok().body(ResultUtil.success("执行成功"));
    }
}
