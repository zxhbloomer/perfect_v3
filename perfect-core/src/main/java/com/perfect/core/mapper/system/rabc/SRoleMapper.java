package com.perfect.core.mapper.system.rabc;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.perfect.bean.entity.client.user.MUserEntity;
import com.perfect.bean.entity.system.rabc.SRoleEntity;
import com.perfect.bean.vo.sys.rabc.role.SysRoleVo;
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

    /**
     * 页面查询列表
     * @param page
     * @param searchCondition
     * @return
     */
    @Select("   "
        + " select t.* "
        + "   from s_role t "
        + "  where (t.name        like CONCAT ('%',#{p1.name,jdbcType=VARCHAR},'%') or #{p1.name,jdbcType=VARCHAR} is null) "
        + "    and (t.code        like CONCAT ('%',#{p1.code,jdbcType=VARCHAR},'%') or #{p1.code,jdbcType=VARCHAR} is null) "
        + "    and (t.simple_name like CONCAT ('%',#{p1.simpleName,jdbcType=VARCHAR},'%') or #{p1.simpleName,jdbcType=VARCHAR} is null) ")
    IPage<SRoleEntity> getListPage(Page page, @Param("p1") SysRoleVo searchCondition );

    @Select("   "
        + " select t.* "
        + "   from s_role t "
        + "  where (t.name        like CONCAT ('%',#{p1.name,jdbcType=VARCHAR},'%') or #{p1.name,jdbcType=VARCHAR} is null) "
        + "    and (t.code        like CONCAT ('%',#{p1.code,jdbcType=VARCHAR},'%') or #{p1.code,jdbcType=VARCHAR} is null) "
        + "    and (t.simple_name like CONCAT ('%',#{p1.simpleName,jdbcType=VARCHAR},'%') or #{p1.simpleName,jdbcType=VARCHAR} is null) ")
    List<SRoleEntity> getAllList(@Param("p1") SysRoleVo searchCondition );
}
