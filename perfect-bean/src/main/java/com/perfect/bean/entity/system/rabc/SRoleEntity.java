package com.perfect.bean.entity.system.rabc;

import com.baomidou.mybatisplus.annotation.*;
import com.perfect.bean.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
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
