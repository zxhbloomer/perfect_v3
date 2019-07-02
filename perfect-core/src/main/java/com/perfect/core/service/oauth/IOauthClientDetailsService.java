package com.perfect.core.service.oauth;

import com.perfect.bean.entity.oauth.OauthClientDetailsEntity;
import com.perfect.common.base.service.v1.IBaseService;
import org.springframework.security.oauth2.provider.ClientDetailsService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2019-05-17
 */
public interface IOauthClientDetailsService extends IBaseService<OauthClientDetailsEntity>, ClientDetailsService {

}
