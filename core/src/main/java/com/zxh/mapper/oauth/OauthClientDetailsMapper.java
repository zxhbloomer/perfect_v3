package com.zxh.mapper.oauth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxh.bean.entity.oauth.OauthClientDetailsEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthClientDetailsMapper
        extends BaseMapper<OauthClientDetailsEntity> {
}
