package com.perfect.serviceimpl.oauth;

import com.perfect.base.service.impl.v1.BaseServiceImpl;
import com.perfect.bean.entity.oauth.OauthCodeEntity;
import com.perfect.mapper.oauth.OauthCodeMapper;
import com.perfect.service.oauth.IOauthCodeService;
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
public class OauthCodeServiceImpl extends BaseServiceImpl<OauthCodeMapper, OauthCodeEntity> implements IOauthCodeService {

}
