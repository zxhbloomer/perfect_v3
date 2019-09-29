package com.perfect.bean.entity.sys.config.config;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 参数配置表
 * </p>
 *
 * @author zxh
 * @since 2019-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("s_config")
public class SConfigEntity implements Serializable {

    private static final long serialVersionUID = 3883756169114451500L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 参数名称
     */
    @TableField("name")
    private String name;

    /**
     * 参数键名
     */
    @TableField("config_key")
    private String config_key;

    /**
     * 参数键值
     */
    @TableField("value")
    private String value;

    /**
     * 是否禁用(1:false-未启用,0:true-已启用)
     */
    @TableField("isenable")
    private Boolean isenable;

    /**
     * 描述
     */
    @TableField("descr")
    private String descr;

    @TableField(value="c_id", fill = FieldFill.INSERT)
    private Long c_id;

    @TableField(value="c_time", fill = FieldFill.INSERT)
    private LocalDateTime c_time;

    @TableField(value="u_id", fill = FieldFill.INSERT_UPDATE)
    private Long u_id;

    @TableField(value="u_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime u_time;

    /**
     * 数据版本，乐观锁使用
     */
    @Version
    @TableField(value="dbversion", fill = FieldFill.INSERT_UPDATE)
    private Integer dbversion;


}
