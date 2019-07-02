package com.perfect.serviceimpl.oauth;

import com.perfect.base.service.impl.v1.BaseServiceImpl;
import com.perfect.bean.entity.oauth.OauthClientTokenEntity;
import com.perfect.mapper.oauth.OauthClientTokenMapper;
import com.perfect.service.oauth.IOauthClientTokenService;
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
public class OauthClientTokenServiceImpl extends BaseServiceImpl<OauthClientTokenMapper, OauthClientTokenEntity> implements IOauthClientTokenService {

}
