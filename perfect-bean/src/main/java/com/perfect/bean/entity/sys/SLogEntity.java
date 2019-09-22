package com.perfect.bean.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.perfect.bean.entity.base.entity.v1.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 日志表
 * </p>
 *
 * @author zxh
 * @since 2019-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("s_log")
public class SLogEntity extends BaseEntity<SLogEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作用户
     */
    @TableField("user_name")
    private String user_name;

    /**
     * 操作描述
     */
    @TableField("operation")
    private String operation;

    /**
     * 耗时（毫秒）
     */
    @TableField("time")
    private Long time;

    @TableField("class_name")
    private String class_name;

    @TableField("class_method")
    private String class_method;

    /**
     * HTTP方法
     */
    @TableField("http_method")
    private String http_method;

    /**
     * 参数
     */
    @TableField("params")
    private String params;

    /**
     * url
     */
    @TableField("url")
    private String url;

    /**
     * IP地址
     */
    @TableField("ip")
    private String ip;

    @TableField("c_time")
    private LocalDateTime c_time;


}
