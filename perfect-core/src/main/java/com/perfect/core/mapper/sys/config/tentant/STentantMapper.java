package com.perfect.core.mapper.sys.config.tentant;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.perfect.bean.entity.sys.config.tenant.STentantEntity;
import com.perfect.bean.vo.sys.config.tenant.STentantTreeVo;
import com.perfect.bean.vo.sys.config.tenant.STentantVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 模块表 Mapper 接口
 * </p>
 *
 * @author zxh
 * @since 2019-08-16
 */
@Repository
public interface STentantMapper extends BaseMapper<STentantEntity> {

    String commonTreeSql = "   "
        + "           with recursive tab1  as (               "
        + "           select t0.id,                                                     "
        + "                  t0.parentid,                                               "
        + "                  1 level,                                                   "
        + "                  t0.name,                                                   "
        + "                  t0.name  as depth_name                                     "
        + "             from s_tenant t0                                                "
        + "            where t0.parentid is null                                        "
        + "            union all                                                        "
        + "            select t2.id,                                                    "
        + "                   t2.parentid,                                              "
        + "                   t1.level + 1 as level,                                    "
        + "                   t2.name,                                                  "
        + "                   CONCAT( t1.name,'>',t2.name)  depth_name                  "
        + "              from s_tenant t2,                                              "
        + "                   tab1 t1                                                   "
        + "             where t2.parentid = t1.id                                       "
        + "             )                                                               "
        + "                select t1.id,                              "
        + "                   t1.parentid,                            "
        + "                   t1.level,                               "
        + "                   t1.name,                                "
        + "                   t1.depth_name,                          "
        + "                   t2.code,                                "
        + "                   t2.name as label,                       "
        + "                   t2.isenable,                            "
        + "                   t2.enable_time,                         "
        + "                   t2.disable_time,                        "
        + "                   t2.isfreeze,                            "
        + "                   t2.isleaf,                              "
        + "                   t2.sort,                                "
        + "                   t2.descr,                               "
        + "                   t2.isdel,                               "
        + "                   t2.c_id,                                "
        + "                   t2.c_time,                              "
        + "                   t2.u_id,                                "
        + "                   t2.u_time,                              "
        + "                   t2.dbversion                            "
        + "              from tab1 t1                                 "
        + "        inner join s_tenant t2 "
        + "                on t1.id = t2.id            "
        + "           ";

    /**
     * 获取树的数据
     * @param id
     * @return
     */
    @Select(
        "       "
        + commonTreeSql
        + "  where (                                                             "
        + "          case                                                        "
        + "             when t1.id = #{p1} then true                             "
        + "             when t1.id <> #{p1} then t1.parentid = #{p1}             "
        + "             else false                                               "
        + "         end                                                          "
        + "         or #{p1} is null                                             "
        + "        )                                                             "
        + "    and (t1.depth_name like CONCAT ('%',#{p2},'%') or #{p2} is null)                                                             "
        + "        ;"
    )
    List<STentantTreeVo> getTreeList(@Param("p1") Long id, @Param("p2") String name);

    /**
     * 页面查询列表
     * 
     * @param page
     * @param searchCondition
     * @return
     */
    @Select("<script>"
        + "     SELECT                                                      "
        + "         t1.*                                                    "
        + "     FROM                                                        "
        + "         s_tenant AS t1                                          "
        + "  where true "
        + "    and (t1.name like CONCAT ('%',#{p1.name,jdbcType=VARCHAR},'%') or #{p1.name,jdbcType=VARCHAR} is null) "
        + "  </script>")
    IPage<STentantVo> selectPage(Page<STentantVo> page, @Param("p1") STentantVo searchCondition);


    /**
     * 没有分页，按id筛选条件
     * 
     * @param searchCondition
     * @return
     */
    @Select("<script>"
        + " select t.* "
        + "   from s_tenant t "
        + "  where t.id in "
        + "        <foreach collection='p1' item='item' index='index' open='(' separator=',' close=')'>"
        + "         #{item.id}  "
        + "        </foreach>"
        + "  </script>")
    List<STentantEntity> selectIdsIn(@Param("p1") List<STentantVo> searchCondition);

    /**
     * 按id查询
     * 
     * @param id
     * @return
     */
    @Select(" "
        + " select t.* "
        + "   from s_tenant t1 "
        + "  where t1.id =  #{p1}"
        + "        ")
    STentantVo selectId(@Param("p1") Long id);

    /**
     * 按条件获取所有数据，没有分页
     * 
     * @param code
     * @return
     */
    @Select("    "
        + " select t.* "
        + "   from s_tenant t "
        + "  where true "
        + "    and t.code =  #{p1}"
        + "      ")
    List<STentantEntity> selectByCode(@Param("p1") String code);

    /**
     * 按条件获取所有数据，没有分页
     * 
     * @param name
     * @return
     */
    @Select("    "
        + " select t.* "
        + "   from s_tenant t "
        + "  where true "
        + "    and t.name =  #{p1}"
        + "      ")
    List<STentantEntity> selectByName(@Param("p1") String name);
}
