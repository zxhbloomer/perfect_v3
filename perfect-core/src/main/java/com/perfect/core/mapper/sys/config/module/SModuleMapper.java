package com.perfect.core.mapper.sys.config.module;

import java.util.List;

import com.perfect.bean.entity.sys.config.module.SModuleEntity;
import com.perfect.bean.vo.sys.module.SModuleVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 模块表 Mapper 接口
 * </p>
 *
 * @author zxh
 * @since 2019-08-16
 */
@Repository
public interface SModuleMapper extends BaseMapper<SModuleEntity> {

    /**
     * 页面查询列表
     * @param page
     * @param searchCondition
     * @return
     */
    @Select("<script>"
        + " select t.* "
        + "   from s_module t "
        + "  where true "
        + "    and (t.name like CONCAT ('%',#{p1.name,jdbcType=VARCHAR},'%') or #{p1.name,jdbcType=VARCHAR} is null) "
        + "   <if test='p1.types != null and p1.types.length!=0' >"
        + "    and t.type in "
        + "        <foreach collection='p1.types' item='item' index='index' open='(' separator=',' close=')'>"
        + "         #{item}  "
        + "        </foreach>"
        + "   </if>"
        + "    and (t.isdel =#{p1.isdel,jdbcType=VARCHAR} or #{p1.isdel,jdbcType=VARCHAR} is null) "
        + "  </script>")
    IPage<SModuleEntity> selectPage(Page page, @Param("p1") SModuleVo searchCondition);

    /**
     * 按条件获取所有数据，没有分页
     * @param searchCondition
     * @return
     */
    @Select("<script>"
        + " select t.* "
        + "   from s_module t "
        + "  where true "
        + "    and (t.name like CONCAT ('%',#{p1.name,jdbcType=VARCHAR},'%') or #{p1.name,jdbcType=VARCHAR} is null) "
        + "   <if test='p1.code.length!=0' >"
        + "    and t.type in "
        + "        <foreach collection='p1.code' item='item' index='index' open='(' separator=',' close=')'>"
        + "         #{item}  "
        + "        </foreach>"
        + "   </if>"
        + "    and (t.isdel =#{p1.isdel,jdbcType=VARCHAR} or #{p1.isdel,jdbcType=VARCHAR} is null) "
        + "  </script>")
    List<SModuleEntity> select(@Param("p1") SModuleVo searchCondition);

    /**
     * 没有分页，按id筛选条件
     * @param searchCondition
     * @return
     */
    @Select("<script>"
        + " select t.* "
        + "   from s_module t "
        + "  where t.id in "
        + "        <foreach collection='p1' item='item' index='index' open='(' separator=',' close=')'>"
        + "         #{item.id}  "
        + "        </foreach>"
        + "  </script>")
    List<SModuleEntity> selectIdsIn(@Param("p1") List<SModuleVo> searchCondition);
}
