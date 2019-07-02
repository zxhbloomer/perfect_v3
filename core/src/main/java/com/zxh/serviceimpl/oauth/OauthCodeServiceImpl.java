package com.zxh.serviceimpl.oauth;

import com.zxh.base.service.impl.v1.BaseServiceImpl;
import com.zxh.bean.entity.oauth.OauthCodeEntity;
import com.zxh.mapper.oauth.OauthCodeMapper;
import com.zxh.service.oauth.IOauthCodeService;
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
