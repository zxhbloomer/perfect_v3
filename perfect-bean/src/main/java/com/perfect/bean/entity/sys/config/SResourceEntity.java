package com.perfect.bean.entity.system.config;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author zxh
 * @since 2019-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("s_resource")
public class SResourceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * excel导入模板文件：10，静态配置文件：20，静态图片文件：30，json

     */
    @TableField("type")
    private String type;

    /**
     * 资源名称
     */
    @TableField("name")
    private String name;

    /**
     * 相对路径
     */
    @TableField("uri")
    private String uri;

    /**
     * 文件系统的baseurl
     */
    @TableField("base")
    private String base;

    /**
     * 文件大小
     */
    @TableField("size")
    private Long size;

    /**
     * 文件扩展名
     */
    @TableField("extension")
    private String extension;

    /**
     * 描述
     */
    @TableField("descr")
    private String descr;

    /**
     * json配置文件
     */
    @TableField("context")
    private String context;

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
