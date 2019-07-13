package com.perfect.core.mapper.client.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.perfect.bean.entity.client.user.MStaffEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 员工 Mapper 接口
 * </p>
 *
 * @author zxh
 * @since 2019-07-13
 */
@Repository
public interface MStaffMapper extends BaseMapper<MStaffEntity> {

    @Select( "   " +
            " select t.* " +
            "   from m_staff t " +
            "  where t.userid = #{p1}")
    MStaffEntity getDataByUserId(@Param("p1") Long p1);
}
