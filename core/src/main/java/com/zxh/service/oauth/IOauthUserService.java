package com.zxh.service.oauth;

import com.zxh.base.service.v1.IBaseService;
import com.zxh.bean.entity.oauth.OauthUserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2019-05-17
 */
public interface IOauthUserService extends IBaseService<OauthUserEntity>,UserDetailsService {

}
