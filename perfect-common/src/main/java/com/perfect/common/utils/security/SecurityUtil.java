package com.perfect.common.utils.security;

import com.perfect.bean.bo.user.login.MUserBo;
import com.perfect.bean.entity.client.user.MUserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 安全类工具类
 */
public class SecurityUtil {

    /**
     * 获取login的Authentication
     * @return
     */
    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }


    /**
     * 获取 MUserEntity
     * @return
     */
    public static MUserEntity getLoginUserEntity(){
        return ((MUserBo) SecurityUtil.getAuthentication().getPrincipal()).getMUserEntity();
    }

    /**
     * 获取login的userid
     * @return
     */
    public static long getLoginUserId(){
        return SecurityUtil.getLoginUserEntity().getId();
    }

    /**
     * 获取Principal
     * @return
     */
    public static Object getPrincipal(){
        return SecurityUtil.getAuthentication().getPrincipal();
    }
}
