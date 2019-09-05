package com.perfect.manager.controller.user;

import com.perfect.bean.pojo.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.perfect.bean.result.utils.v1.ResultUtil;
import com.perfect.bean.vo.user.info.UserInfoVo;
import com.perfect.common.annotation.SysLog;
import com.perfect.core.service.client.user.IMUserService;
import com.perfect.framework.base.controller.v1.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zxh
 */
@RestController
@RequestMapping(value = "/api/v1/user")
@Slf4j
@Api("用户相关")
public class UserController extends BaseController {

    @Autowired
    private IMUserService imUserService;

    @SysLog("获取用户信息")
    @ApiOperation("获取用户信息")
    @GetMapping("/info")
    @ResponseBody
    public ResponseEntity<JsonResult<UserInfoVo>> userInfo(@RequestParam("token") String token) {

        UserInfoVo userInfoVo = imUserService.getUserInfo(token);

//        ResponseEntity<OAuth2AccessToken
        return ResponseEntity.ok().body(ResultUtil.OK(userInfoVo));
    }

    @SysLog("登出")
    @ApiOperation("登出")
    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<JsonResult<String>> logout() {
        return ResponseEntity.ok().body(ResultUtil.OK("登出成功"));
    }
}
