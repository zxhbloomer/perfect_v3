package com.perfect.bean.vo.sys.config.dict;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import com.perfect.bean.pojo.fs.UploadFileResultPojo;
import com.perfect.bean.vo.condition.common.PageCondition;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long dict_Type_Id;

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
    private String value;

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
    private String corpCode;

    /**
     * 租户名称
     */
    private String corpName;

    private Long cId;

    private LocalDateTime cTime;

    private Long uId;

    private LocalDateTime uTime;

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
    private String dict_Type_name;

    /**
     * 字典编号：唯一
     */
    private String dict_Type_code;

    /**
     * 描述
     */
    private String dict_Type_descr;

    /**
     * 是否删除
     */
    private Boolean dict_Type_isdel;


    /**
     * 换页条件
     */
    private PageCondition pageCondition;

}
