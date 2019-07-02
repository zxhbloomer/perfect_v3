package com.perfect.core.serviceimpl.oauth;

import com.perfect.bean.entity.oauth.OauthApprovalsEntity;
import com.perfect.common.base.service.impl.v1.BaseServiceImpl;
import com.perfect.core.mapper.oauth.OauthApprovalsMapper;
import com.perfect.core.service.oauth.IOauthApprovalsService;
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
public class OauthApprovalsServiceImpl extends BaseServiceImpl<OauthApprovalsMapper, OauthApprovalsEntity> implements IOauthApprovalsService {

}
