package com.perfect.bean.entity.quartz;

import com.baomidou.mybatisplus.annotation.*;
import com.perfect.bean.entity.base.entity.v1.BaseEntity;
import com.perfect.bean.entity.system.rabc.SRoleEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 任务主表
 * </p>
 *
 * @author zxh
 * @since 2019-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("j_job_master")
public class JJobMasterEntity extends BaseEntity<JJobMasterEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务名称
     */
    @TableField("job_name")
    private String jobName;

    /**
     * 任务编号
     */
    @TableField("job_serial_code")
    private String jobSerialCode;

    /**
     * 任务描述
     */
    @TableField("job_desc")
    private String jobDesc;

    /**
     * 任务简称
     */
    @TableField("job_simple_name")
    private String jobSimpleName;

    /**
     * Bean名称
     */
    @TableField("bean_name")
    private String beanName;

    /**
     * 方法名称
     */
    @TableField("method_name")
    private String methodName;

    /**
     * 参数
     */
    @TableField("params")
    private String params;

    /**
     * 表达式
     */
    @TableField("cron_expression")
    private String cronExpression;

    /**
     * 是否是已经删除

     */
    @TableField(value = "isdel", fill = FieldFill.INSERT)
    private Boolean isdel;

    /**
     * 是否有效
     */
    @TableField("is_effected")
    private Boolean isEffected;

    /**
     * 下次运行时间
     */
    @TableField("run_time")
    private LocalDateTime runTime;

    /**
     * 上次运行时间
     */
    @TableField("last_run_time")
    private LocalDateTime lastRunTime;

    /**
     * 起始有效时间
     */
    @TableField("begin_date")
    private LocalDateTime beginDate;

    /**
     * 结束有效时间
     */
    @TableField("end_date")
    private LocalDateTime endDate;

    /**
     * 运行次数
     */
    @TableField("run_times")
    private Integer runTimes;

    @TableField(value="c_id", fill = FieldFill.INSERT)
    private Long cId;

    @TableField(value="c_time", fill = FieldFill.INSERT)
    private LocalDateTime cTime;

    @TableField(value="u_id", fill = FieldFill.INSERT_UPDATE)
    private Long uId;

    @TableField(value="u_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime uTime;


}
