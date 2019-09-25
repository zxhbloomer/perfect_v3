package com.perfect.bean.vo.sys.config.dict;

import com.perfect.bean.pojo.fs.UploadFileResultPojo;
import com.perfect.bean.vo.common.condition.PageCondition;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhangxh
 */
@Data
@NoArgsConstructor
@ApiModel(value = "字典数据信息", description = "字典数据vo_bean")
public class SDictDataVo extends UploadFileResultPojo implements Serializable {

    private static final long serialVersionUID = 835262693681898034L;

    private Long id;

    /**
     * 字典类型表id主键
     */
    private Long dict_type_id;

    /**
     * 字典排序
     */
    private Integer sort;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典键值
     */
    private String dict_value;

    /**
     * 描述
     */
    private String descr;

    /**
     * 是否删除
     */
    private Boolean isdel;

    /**
     * 租户代码
     */
    private String corp_code;

    /**
     * 租户名称
     */
    private String corp_name;

    private Long c_id;

    private LocalDateTime c_time;

    private Long u_id;

    private LocalDateTime u_time;

    /**
     * 数据版本，乐观锁使用
     */
    private Integer dbversion;

    /**
     * 以下是字典分类属性
     */

    /**
     * 字典名称
     */
    private String dictTypeName;

    /**
     * 字典类型：唯一
     */
    private String dictTypeCode;

    /**
     * 字典类型描述
     */
    private String dictTypeDescr;

    /**
     * 排序的最大最小值
     */
    private int max_sort;
    private int min_sort;

    /**
     * 是否删除
     */
    private Boolean dictTypeIsdel;


    /**
     * 换页条件
     */
    private PageCondition pageCondition;

}
