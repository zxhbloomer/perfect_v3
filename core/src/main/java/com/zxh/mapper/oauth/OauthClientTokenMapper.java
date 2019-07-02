package com.zxh.mapper.oauth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxh.bean.entity.oauth.OauthClientTokenEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthClientTokenMapper
        extends BaseMapper<OauthClientTokenEntity> {
}
