package com.zxh.serviceimpl.oauth;

import com.zxh.base.service.impl.v1.BaseServiceImpl;
import com.zxh.bean.entity.oauth.OauthAccessTokenEntity;
import com.zxh.mapper.oauth.OauthAccessTokenMapper;
import com.zxh.service.oauth.IOauthAccessTokenService;
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
