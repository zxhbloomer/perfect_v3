package com.perfect.serviceimpl.oauth;

import com.perfect.base.service.impl.v1.BaseServiceImpl;
import com.perfect.bean.entity.oauth.PersistentLoginsEntity;
import com.perfect.mapper.oauth.PersistentLoginsMapper;
import com.perfect.service.oauth.IPersistentLoginsService;
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
public class PersistentLoginsServiceImpl extends BaseServiceImpl<PersistentLoginsMapper, PersistentLoginsEntity> implements IPersistentLoginsService {

}
