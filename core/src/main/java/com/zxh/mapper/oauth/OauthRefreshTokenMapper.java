package com.zxh.mapper.oauth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxh.bean.entity.oauth.OauthRefreshTokenEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthRefreshTokenMapper
        extends BaseMapper<OauthRefreshTokenEntity> {
}
