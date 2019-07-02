package com.zxh.controller;

import com.zxh.base.controller.v1.BaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/login")
public class HelloWorldController extends BaseController {
    @RequestMapping(value="/hello",method = {RequestMethod.POST,RequestMethod.GET})
    public String index() {
        return "Hello World";
    }

    @ApiOperation(value = "登录认证服务器")
    @RequestMapping(value="/oauth",method = {RequestMethod.POST,RequestMethod.GET})
    public String oauth() {
        // http://127.0.0.1:8089/oauth/authorize?response_type=code&client_id=client&redirect_uri=http://127.0.0.1:8088&scope=select

        return "oauth";
    }
}
