package com.perfect.core.mapper.sys.config.dict;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.perfect.bean.entity.sys.config.dict.SDictDataEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.perfect.bean.entity.sys.config.dict.SDictTypeEntity;
import com.perfect.bean.vo.sys.config.dict.SDictDataVo;
import com.perfect.bean.vo.sys.config.dict.SDictTypeVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 字典数据表 Mapper 接口
 * </p>
 *
 * @author zxh
 * @since 2019-08-23
 */
@Repository
public interface SDictDataMapper extends BaseMapper<SDictDataEntity> {

    /**
     * 页面查询列表
     * @param page
     * @param searchCondition
     * @return
     */
    @Select("<script>"
        + " select t.* "
        + "   from s_resource t "
        + "  where true "
        + "    and (t.name like CONCAT ('%',#{p1.name,jdbcType=VARCHAR},'%') or #{p1.name,jdbcType=VARCHAR} is null) "
        + "   <if test='p1.code != null and p1.code.length!=0' >"
        + "    and t.type in "
        + "        <foreach collection='p1.code' item='item' index='index' open='(' separator=',' close=')'>"
        + "         #{item}  "
        + "        </foreach>"
        + "   </if>"
        + "    and (t.isdel =#{p1.isdel,jdbcType=VARCHAR} or #{p1.isdel,jdbcType=VARCHAR} is null) "
        + "  </script>")
    IPage<SDictDataEntity> selectPage(Page page, @Param("p1") SDictDataVo searchCondition );

    /**
     * 按条件获取所有数据，没有分页
     * @param searchCondition
     * @return
     */
    @Select("<script>"
        + " select t.* "
        + "   from s_resource t "
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
    List<SDictDataEntity> select(@Param("p1") SDictDataVo searchCondition );

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
    List<SDictDataEntity> selectIdsIn(@Param("p1") List<SDictDataVo> searchCondition );
}
