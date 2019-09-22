package com.perfect.bean.entity.client.user;

import com.baomidou.mybatisplus.annotation.*;
import com.perfect.bean.entity.base.entity.v1.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 员工
 * </p>
 *
 * @author zxh
 * @since 2019-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("m_staff")
public class MStaffEntity extends BaseEntity<MStaffEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户
     */
    @TableField("userid")
    private Long userid;

    @TableField("deptid")
    private Long deptid;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 描述
     */
    @TableField("descr")
    private String descr;

    /**
     * 生日
     */
    @TableField("birthday")
    private LocalDate birthday;

    @TableField("office_telno")
    private String officeTelno;

    @TableField("idcardno")
    private String idcardno;

    /**
     * 婚否
     */
    @TableField("iswed")
    private Boolean iswed;

    /**
     * 名族
     */
    @TableField("nation")
    private String nation;

    /**
     * 是否在职
在职=1,不在职=0,离职=2,离退休=3,返聘=4
     */
    @TableField("service")
    private Boolean service;

    @TableField(value="c_id", fill = FieldFill.INSERT)
    private Long c_id;

    @TableField(value="c_time", fill = FieldFill.INSERT)
    private LocalDateTime c_time;

    @TableField(value="u_id", fill = FieldFill.INSERT_UPDATE)
    private Long u_id;

    @Version
    @TableField(value="u_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime u_time;

    /**
     * 数据版本，乐观锁使用
     */
    @Version
    @TableField(value="dbversion", fill = FieldFill.INSERT_UPDATE)
    private Integer dbversion;
}
