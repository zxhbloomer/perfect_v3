package com.perfect.core.mapper.oauth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.perfect.bean.entity.oauth.OauthAccessTokenEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthAccessTokenMapper extends BaseMapper<OauthAccessTokenEntity> {
}
