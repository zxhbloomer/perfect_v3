package com.perfect.bean.entity.sys.config.dict;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.perfect.bean.entity.base.entity.v1.BaseEntity;
import com.perfect.bean.entity.sys.config.resource.SResourceEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 字典类型表、字典主表
 * </p>
 *
 * @author zxh
 * @since 2019-08-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("s_dict_type")
public class SDictTypeEntity extends BaseEntity<SDictTypeEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典名称
     */
    @TableField("name")
    private String name;

    /**
     * 字典类型
     */
    @TableField("type")
    private String type;

    /**
     * 描述
     */
    @TableField("descr")
    private String descr;

    /**
     * 是否删除
     */
    @TableField(value = "isdel", fill = FieldFill.INSERT)
    private Boolean isdel;

    /**
     * 租户代码
     */
    @TableField("corp_code")
    private String corpCode;

    /**
     * 租户名称
     */
    @TableField("corp_name")
    private String corpName;

    @TableField(value="c_id", fill = FieldFill.INSERT)
    private Long cId;

    @TableField(value="c_time", fill = FieldFill.INSERT)
    private LocalDateTime cTime;

    @TableField(value="u_id", fill = FieldFill.INSERT_UPDATE)
    private Long uId;

    @TableField(value="u_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime uTime;

    /**
     * 数据版本，乐观锁使用
     */
    @Version
    @TableField(value="dbversion", fill = FieldFill.INSERT_UPDATE)
    private Integer dbversion;

}
