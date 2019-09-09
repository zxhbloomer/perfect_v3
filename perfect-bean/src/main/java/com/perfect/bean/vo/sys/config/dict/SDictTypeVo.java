package com.perfect.bean.vo.sys.config.dict;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@ApiModel(value = "字典类型信息", description = "字典类型vo_bean")
public class SDictTypeVo extends UploadFileResultPojo implements Serializable {

    private static final long serialVersionUID = 8149295048471235932L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典编号：唯一
     */
    private String code;

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
     * 换页条件
     */
    private PageCondition pageCondition;
}
