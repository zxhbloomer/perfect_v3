package com.perfect.core.serviceimpl.oauth;

import com.perfect.bean.entity.oauth.OauthAccessTokenEntity;
import com.perfect.common.base.service.impl.v1.BaseServiceImpl;
import com.perfect.core.mapper.oauth.OauthAccessTokenMapper;
import com.perfect.core.service.oauth.IOauthAccessTokenService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-05-17
 */
@Service
public class OauthAccessTokenServiceImpl extends BaseServiceImpl<OauthAccessTokenMapper, OauthAccessTokenEntity> implements IOauthAccessTokenService {

}
