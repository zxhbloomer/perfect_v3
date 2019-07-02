package com.perfect.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.perfect.base.controller.v1.BaseController;
import com.perfect.bean.model.TokenLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Base64;
import java.util.Collections;

@RestController
@RequestMapping(value = "/token/v1")
@Slf4j
@Api(value = "令牌接口")
public class TokenController extends BaseController {

    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;
    @Autowired
    private OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 通过密码授权方式向授权服务器获取令牌，验证服务器
     * @param tokenLoginVo
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "登录认证服务器，获取token")
    @PostMapping(value = "/login")
    public OAuth2AccessToken login(@RequestBody @Valid TokenLoginVo tokenLoginVo, BindingResult bindingResult)  throws Exception{
        if (bindingResult.hasErrors()) {
            throw new Exception("登录信息格式错误");
        } else {
            //Http Basic 验证"http://127.0.0.1:8089/oauth/authorize"
            String clientAndSecret = oAuth2ClientProperties.getClientId()+":"+oAuth2ClientProperties.getClientSecret();
            //这里需要注意为 Basic 而非 Bearer
            clientAndSecret = "Basic "+ Base64.getEncoder().encodeToString(clientAndSecret.getBytes());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization",clientAndSecret);
            //授权请求信息
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.put("client_id", Collections.singletonList(oAuth2ProtectedResourceDetails.getClientId()));
            map.put("client_secret", Collections.singletonList(oAuth2ProtectedResourceDetails.getClientSecret()));
            map.put("grant_type", Collections.singletonList(oAuth2ProtectedResourceDetails.getGrantType()));
            map.put("username", Collections.singletonList(tokenLoginVo.getUsername()));
            map.put("password", Collections.singletonList(tokenLoginVo.getPassword()));
            map.put("scope", oAuth2ProtectedResourceDetails.getScope());
            //HttpEntity
            HttpEntity httpEntity = new HttpEntity(map,httpHeaders);
            //获取 jwt Token 对象
            ResponseEntity<String> rtnString = restTemplate.exchange(oAuth2ProtectedResourceDetails.getAccessTokenUri(), HttpMethod.POST,httpEntity, String.class);
            DefaultOAuth2AccessToken oat = JSON.parseObject(rtnString.getBody(), new TypeReference<DefaultOAuth2AccessToken>() {});
            return oat;
        }
    }
}
