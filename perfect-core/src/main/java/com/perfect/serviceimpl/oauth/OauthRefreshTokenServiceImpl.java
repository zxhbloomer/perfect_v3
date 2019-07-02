package com.perfect.serviceimpl.oauth;

import com.perfect.base.service.impl.v1.BaseServiceImpl;
import com.perfect.bean.entity.oauth.OauthRefreshTokenEntity;
import com.perfect.mapper.oauth.OauthRefreshTokenMapper;
import com.perfect.service.oauth.IOauthRefreshTokenService;
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
public class OauthRefreshTokenServiceImpl extends BaseServiceImpl<OauthRefreshTokenMapper, OauthRefreshTokenEntity> implements IOauthRefreshTokenService {

}
