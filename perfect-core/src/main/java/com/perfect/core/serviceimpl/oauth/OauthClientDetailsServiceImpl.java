package com.perfect.core.serviceimpl.oauth;

import com.perfect.bean.entity.oauth.OauthClientDetailsEntity;
import com.perfect.common.base.service.impl.v1.BaseServiceImpl;
import com.perfect.common.security.bean.BootClientDetails;
import com.perfect.core.mapper.oauth.OauthClientDetailsMapper;
import com.perfect.core.service.oauth.IOauthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
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
public class OauthClientDetailsServiceImpl extends BaseServiceImpl<OauthClientDetailsMapper, OauthClientDetailsEntity> implements IOauthClientDetailsService {
    @Autowired
    private OauthClientDetailsMapper oauthClientDetailsMapper;

    @Override
    public ClientDetails loadClientByClientId(String id) throws ClientRegistrationException {
        OauthClientDetailsEntity oauthClientDetailsEntity = oauthClientDetailsMapper.selectById(id);

        if (oauthClientDetailsEntity == null) {
            throw new ClientRegistrationException("没有找到client信息");
        }

//        BaseClientDetails baseClientDetails =
//                new BaseClientDetails(
//                        oauthClientDetailsEntity.getClientId(),
//                        StringUtils.join(oauthClientDetailsEntity.getResourceIds(), ","),
//                        StringUtils.join(oauthClientDetailsEntity.getScope(), ","),
//                        StringUtils.join(oauthClientDetailsEntity.getAuthorizedGrantTypes(), ","),
//                        StringUtils.join(oauthClientDetailsEntity.getAuthorities(), ","));
//        return baseClientDetails;

        BootClientDetails details=new BootClientDetails(oauthClientDetailsEntity);

        return details;
    }
}
