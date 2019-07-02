package com.zxh.mapper.oauth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxh.bean.entity.oauth.OauthCodeEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthCodeMapper
        extends BaseMapper<OauthCodeEntity> {
}
