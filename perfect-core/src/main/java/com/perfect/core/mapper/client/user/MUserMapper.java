package com.perfect.core.mapper.client.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.perfect.bean.entity.client.user.MUserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-06-24
 */
@Repository
public interface MUserMapper extends BaseMapper<MUserEntity> {

    /**
     *
     * @param p1
     * @return
     */
    @Select( "   " +
            " select t.* " +
            "   from m_user t " +
            "  where t.login_name = #{p1}")
    MUserEntity getDataByName(@Param("p1") String p1);
}
