package com.perfect.bean.entity.client.user;

import com.baomidou.mybatisplus.annotation.*;
import com.perfect.bean.entity.base.entity.v1.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 简单
 * </p>
 *
 * @author zxh
 * @since 2019-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_user_lite")
public class MUserLiteEntity extends BaseEntity<MUserLiteEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("login_name")
    private String loginName;

    @TableField("name")
    private String name;

    @TableField("simple_name")
    private String simpleName;

    /**
     * 系统用户=10,职员=20,客户=30,供应商=40,其他=50,认证管理员=60,审计管理员=70
     */
    @TableField("type")
    private String type;

    /**
     * 描述
     */
    @TableField("descr")
    private String descr;

    /**
     * 密码

     */
    @TableField("pwd")
    private String pwd;

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

    @TableField("avatar")
    private String avatar;

    @TableField(value="c_id", fill = FieldFill.INSERT)
    private Long cId;

    @TableField(value="c_time", fill = FieldFill.INSERT)
    private LocalDateTime cTime;

    @TableField(value="u_id", fill = FieldFill.INSERT_UPDATE)
    private Long uId;

    @TableField(value="u_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime uTime;

}
