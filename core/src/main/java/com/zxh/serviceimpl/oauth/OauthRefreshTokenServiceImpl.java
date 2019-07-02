package com.zxh.serviceimpl.oauth;

import com.zxh.base.service.impl.v1.BaseServiceImpl;
import com.zxh.bean.entity.oauth.OauthRefreshTokenEntity;
import com.zxh.mapper.oauth.OauthRefreshTokenMapper;
import com.zxh.service.oauth.IOauthRefreshTokenService;
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
