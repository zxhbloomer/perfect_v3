package com.perfect.service.oauth;

import com.perfect.base.service.v1.IBaseService;
import com.perfect.bean.entity.oauth.OauthUserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

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
