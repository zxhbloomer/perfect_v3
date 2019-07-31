package com.perfect.core.mapper.system.rabc;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.perfect.bean.entity.client.user.MUserEntity;
import com.perfect.bean.entity.system.rabc.SRoleEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author zxh
 * @since 2019-07-11
 */
@Repository
public interface SRoleMapper extends BaseMapper<SRoleEntity> {

    @Select( "   " +
        " select t.* " +
        "   from s_role t " +
        "  where (t.name = #{p1} or #{p1} is null) " +
        "    and (t.code = #{p2} or #{p2} is null) " +
        "    and (t.simple_name = #{p3} or #{p3} is null) ")
    IPage<SRoleEntity> getList(@Param("p1") String name, @Param("p2") String code, @Param("p3") String simple_name,
        Page page);
}
