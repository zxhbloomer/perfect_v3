package com.zxh.serviceimpl.oauth;

import com.zxh.base.service.impl.v1.BaseServiceImpl;
import com.zxh.bean.entity.oauth.PersistentLoginsEntity;
import com.zxh.mapper.oauth.PersistentLoginsMapper;
import com.zxh.service.oauth.IPersistentLoginsService;
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
