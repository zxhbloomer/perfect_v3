package com.perfect.security.handler;

import com.perfect.bean.result.utils.v1.ResponseResultUtil;
import com.perfect.common.constant.PerfectConstant;
import com.perfect.core.service.client.user.IMUserService;
import com.perfect.core.utils.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功处理器
 */
public class PerfectAuthenticationSucessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private IMUserService iMUserService;

    private SessionRegistry sessionRegistry;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // 设置返回值
//        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();

        response.setContentType(PerfectConstant.JSON_UTF8);
//        response.getWriter().write(mapper.writeValueAsString(ResponseBo.ok()));
        Map<String,String> token = new HashMap<String,String>();
        token.put("token",getSessionId(authentication, request.getSession().getId()));

        // 处理缓存
        setUserSession(SecurityUtil.getLoginUserId());

        ResponseResultUtil.responseWriteOK(token, response);
    }
    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    /**
     * 获取sessionid
     * @param authentication
     * @param dflt
     * @return
     */
    private String getSessionId(Authentication authentication, String dflt) {
        if (authentication != null && authentication.isAuthenticated() && authentication.getDetails() instanceof WebAuthenticationDetails) {
            String sessionId = ((WebAuthenticationDetails) authentication.getDetails()).getSessionId();
            return sessionId == null ? dflt : sessionId;
        } else {
            // anonymous
            return dflt;
        }
    }

    /**
     * 执行usersession往session中保存的逻辑
     * @param userid
     */
    public void setUserSession(long userid){
        iMUserService.getUserInSessionBean(userid);
    }
}
