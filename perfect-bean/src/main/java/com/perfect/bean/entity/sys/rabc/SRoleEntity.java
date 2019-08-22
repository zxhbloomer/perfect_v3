package com.perfect.bean.entity.sys.rabc;

import com.baomidou.mybatisplus.annotation.*;
import com.perfect.bean.entity.base.entity.v1.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author zxh
 * @since 2019-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("s_role")
public class SRoleEntity extends BaseEntity<SRoleEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色类型
     */
    @TableField("type")
    private String type;

    /**
     * 角色编码
     */
    @TableField("code")
    private String code;

    /**
     * 角色名称
     */
    @TableField("name")
    private String name;

    /**
     * 描述
     */
    @TableField("descr")
    private String descr;

    /**
     * 简称
     */
    @TableField("simple_name")
    private String simpleName;

    /**
     * 是否是已经删除(1:true-已删除,0:false-未删除)
     * 
     */
    @TableField(value = "isdel", fill = FieldFill.INSERT)
    private Boolean isdel;

    /**
     * 是否禁用(1:true-未启用,0:false-已启用)
     */
    @TableField(value = "isenable", fill = FieldFill.INSERT)
    private Boolean isenable;

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

    @TableField(value = "c_id", fill = FieldFill.INSERT)
    private Long cId;

    @TableField(value = "c_time", fill = FieldFill.INSERT)
    private LocalDateTime cTime;

    @TableField(value = "u_id", fill = FieldFill.INSERT_UPDATE)
    private Long uId;

    @TableField(value = "u_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime uTime;

    /**
     * 数据版本，乐观锁使用
     */
    @Version
    @TableField(value = "dbversion", fill = FieldFill.INSERT_UPDATE)
    private Integer dbversion;
}
