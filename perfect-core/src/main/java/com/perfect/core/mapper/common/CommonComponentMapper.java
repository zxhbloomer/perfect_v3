package com.perfect.core.mapper.common;

import com.perfect.bean.vo.common.component.NameAndValueVo;
import com.perfect.bean.vo.common.component.PerfectComponentVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 获取下拉选项的 Mapper 接口
 * </p>
 *
 * @author zxh
 * @since 2019-07-13
 */
@Repository
public interface CommonComponentMapper extends BaseMapper<NameAndValueVo> {

    String common_select_column = "  "
        + "  SELECT                                                             "
        + "       t2.label as `name`,                                                     "
        + "       t2.dict_value as `value`,                                                 "
        + "       t1.`name` as dict_type_code,                                   "
        + "       t2.id as dict_data_id                                          "
        + "    ";

    @Select( "   "
        +     common_select_column
        + "    FROM                                                              "
        + "       s_dict_type t1                                                 "
        + "       INNER JOIN s_dict_data t2 ON t1.id = t2.dict_type_id           "
        + "       AND t1.isdel = 0                                               "
        + "       AND t2.isdel = 0                                               "
        + "       and t1.code = #{p1}                                             "
        + "     order by t2.sort    "
        + "      ")
    List<NameAndValueVo> getSelectDictDataNormal(@Param("p1") String dict_type_code);
}
