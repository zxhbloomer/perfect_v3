package com.perfect.manager.controller.user;

import com.perfect.bean.vo.TokenLoginVo;
import com.perfect.bean.vo.user.info.UserInfoVo;
import com.perfect.common.annotation.SysLog;
import com.perfect.common.base.controller.v1.BaseController;
import com.perfect.core.service.client.user.IMUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/user")
@Slf4j
@Api("用户相关")
public class UserInfoController extends BaseController {

    @Autowired
    private IMUserService imUserService;

    @SysLog("获取用户信息")
    @ApiOperation("获取用户信息")
    @GetMapping("/info")
    public UserInfoVo userInfo(@RequestParam("token") String token) {

        UserInfoVo rtnBean = imUserService.getUserInfo(token);
//        ResponseEntity<OAuth2AccessToken
        return rtnBean;
    }
}
