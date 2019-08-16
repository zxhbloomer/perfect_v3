package com.perfect.bean.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author zxh
 * @since 2019-08-16
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
     * excel导入模板文件：10，静态配置文件：20，静态图片文件：30

     */
    @TableField("type")
    private String type;

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
     * 租户代码
     */
    @TableField("corp_code")
    private String corpCode;

    /**
     * 租户名称
     */
    @TableField("corp_name")
    private String corpName;

    @TableField("descr")
    private String descr;

    /**
     * 是否删除
     */
    @TableField("isdel")
    private Boolean isdel;

    @TableField("c_id")
    private Long cId;

    @TableField("c_time")
    private LocalDateTime cTime;

    @TableField("u_id")
    private Long uId;

    @TableField("u_time")
    private LocalDateTime uTime;

    /**
     * 数据版本，乐观锁使用
     */
    @TableField("dbversion")
    private Integer dbversion;


}
