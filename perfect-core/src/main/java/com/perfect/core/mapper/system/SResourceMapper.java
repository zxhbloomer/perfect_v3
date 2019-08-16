package com.perfect.core.mapper.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.perfect.bean.entity.system.SResourceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.perfect.bean.entity.system.rabc.SRoleEntity;
import com.perfect.bean.vo.sys.resource.SResourceVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author zxh
 * @since 2019-08-16
 */
public interface SResourceMapper extends BaseMapper<SResourceEntity> {

    /**
     * 页面查询列表
     * @param page
     * @param searchCondition
     * @return
     */
    @Select("   "
        + " select t.* "
        + "   from s_resource t "
        + "  where (t.type        = #{p1.type,jdbcType=VARCHAR} or #{p1.type,jdbcType=VARCHAR} is null) "
        + "    and (t.name        like CONCAT ('%',#{p1.name,jdbcType=VARCHAR},'%') or #{p1.name,jdbcType=VARCHAR} is null) ")
    IPage<SResourceEntity> selectPage(Page page, @Param("p1") SResourceVo searchCondition );

    /**
     * 按条件获取所有数据，没有分页
     * @param searchCondition
     * @return
     */
    @Select("   "
        + " select t.* "
        + "   from s_resource t "
        + "  where (t.type        = #{p1.type,jdbcType=VARCHAR} or #{p1.type,jdbcType=VARCHAR} is null) "
        + "    and (t.name        like CONCAT ('%',#{p1.name,jdbcType=VARCHAR},'%') or #{p1.name,jdbcType=VARCHAR} is null) ")
    List<SResourceEntity> select(@Param("p1") SResourceVo searchCondition );

    /**
     * 没有分页，按id筛选条件
     * @param searchCondition
     * @return
     */
    @Select("<script>"
        + " select t.* "
        + "   from s_resource t "
        + "  where t.id in "
        + "        <foreach collection='p1' item='item' index='index' open='(' separator=',' close=')'>"
        + "         #{item.id}  "
        + "        </foreach>"
        + "  </script>")
    List<SResourceEntity> selectIdsIn(@Param("p1") List<SResourceVo> searchCondition );
}
