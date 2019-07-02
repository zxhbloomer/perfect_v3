package com.zxh.serviceimpl.oauth;

import com.zxh.base.service.impl.v1.BaseServiceImpl;
import com.zxh.bean.entity.oauth.OauthClientTokenEntity;
import com.zxh.mapper.oauth.OauthClientTokenMapper;
import com.zxh.service.oauth.IOauthClientTokenService;
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
