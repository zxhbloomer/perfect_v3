package com.perfect.core.mapper.oauth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.perfect.bean.entity.oauth.PersistentLoginsEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PersistentLoginsMapper
        extends BaseMapper<PersistentLoginsEntity> {
}
