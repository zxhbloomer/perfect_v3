package com.perfect.core.mapper.oauth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.perfect.bean.entity.oauth.OauthUserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthUserMapper extends BaseMapper<OauthUserEntity> {

    @Select( "   " +
            " select t.* " +
            "   from oauth_user t " +
            "  where t.name = #{p1}")
    public OauthUserEntity getDataByName(@Param("p1") String p1);
}
