package com.zxh.mapper.oauth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxh.bean.entity.oauth.PersistentLoginsEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PersistentLoginsMapper
        extends BaseMapper<PersistentLoginsEntity> {
}
