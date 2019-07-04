package com.perfect.bean.entity.system;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxh
 * @since 2019-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("s_log")
public class SLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作用户
     */
    @TableField("user_name")
    private String userName;

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

    /**
     * 操作方法
     */
    @TableField("method")
    private String method;

    /**
     * 参数
     */
    @TableField("params")
    private String params;

    /**
     * IP地址
     */
    @TableField("ip")
    private String ip;

    @TableField(value="c_time", fill = FieldFill.INSERT)
    private LocalDateTime cTime;

    /**
     * 地点
     */
    @TableField("location")
    private String location;


}
