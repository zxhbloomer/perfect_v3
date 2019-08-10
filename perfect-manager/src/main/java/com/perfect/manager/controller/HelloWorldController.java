package com.perfect.manager.controller;

import com.perfect.framework.base.controller.v1.BaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author zxh
 */
@RestController
@RequestMapping(value = "/api/v1/login")
public class HelloWorldController extends BaseController {
    @RequestMapping(value="/hello",method = {RequestMethod.POST,RequestMethod.GET})
    public String index() {
        return "Hello World";
    }

    @ApiOperation(value = "登录认证服务器")
    @RequestMapping(value="/oauth",method = {RequestMethod.POST,RequestMethod.GET})
    public String oauth(HttpServletRequest request) {
        // http://127.0.0.1:8089/oauth/authorize?response_type=code&client_id=client&redirect_uri=http://127.0.0.1:8088&scope=select
        Principal principal = request.getUserPrincipal();
        return "oauth";
    }
}
