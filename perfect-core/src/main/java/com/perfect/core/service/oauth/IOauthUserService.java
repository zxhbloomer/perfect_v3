package com.perfect.core.service.oauth;

import com.perfect.bean.entity.oauth.OauthUserEntity;
import com.perfect.common.base.service.v1.IBaseService;
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
