package com.perfect.bean.entity.client.user;

import com.baomidou.mybatisplus.annotation.*;
import com.perfect.bean.entity.base.entity.v1.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户主表
 * </p>
 *
 * @author zxh
 * @since 2019-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("m_user")
public class MUserEntity extends BaseEntity<MUserEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("login_name")
    private String loginName;

    /**
     * 用户实名
     */
    @TableField("name")
    private String name;

    /**
     * 简称
     */
    @TableField("simple_name")
    private String simple_name;

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
     * 是否是已经删除

     */
    @TableField(value = "isdel", fill = FieldFill.INSERT)
    private Boolean isdel;

    /**
     * 是否锁定

     */
    @TableField("islock")
    private Boolean islock;

    /**
     * 是否禁用 1:false-未启用,0:true-已启用
     */
    @TableField("isenable")
    private Boolean isenable;

    /**
     * 生效时间

     */
    @TableField("effective_date")
    private LocalDateTime effectiveDate;

    /**
     * 失效时间

     */
    @TableField("invalidation_date")
    private LocalDateTime invalidationDate;

    /**
     * 登录错误次数

     */
    @TableField("err_count")
    private Integer errCount;

    /**
     * 所属用户组
用户组织范围
包含下级组织的组织范围

     */
    @TableField("group_id")
    private String groupId;

    @TableField("staff_id")
    private Long staffId;

    /**
     * 密码生效日期

     */
    @TableField("pwd_effective_date")
    private LocalDateTime pwdEffectiveDate;

    /**
     * 用户锁定时间
     */
    @TableField("locked_time")
    private LocalDateTime lockedTime;

    /**
     * 是否为业务管理员
     */
    @TableField("is_biz_admin")
    private Boolean isBizAdmin;

    /**
     * 是否修改过密码
     */
    @TableField("is_changed_pwd")
    private Boolean isChangedPwd;

    /**
     * 传统认证方式=0,智能钥匙认证=1,动态密码锁=2,指纹认证方式=3
     */
    @TableField("login_author_way")
    private Boolean loginAuthorWay;

    /**
     * 历史密码
     */
    @TableField("pwd_his_pwd")
    private String pwdHisPwd;

    @TableField("email")
    private String email;

    /**
     * 家庭电话
     */
    @TableField("home_telno")
    private String homeTelno;

    /**
     * 办公室电话
     */
    @TableField("office_telno")
    private String officeTelno;

    /**
     * 手机号码
     */
    @TableField("cell_telno")
    private String cellTelno;

    /**
     * 租户代码
     */
    @TableField("corp_code")
    private String corp_code;

    /**
     * 租户名称
     */
    @TableField("corp_name")
    private String corp_name;

    /**
     * 性别
     */
    @TableField("sex")
    private Boolean sex;

    /**
     * 头像路径
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 最后登陆时间
     */
    @TableField("last_login_date")
    private LocalDateTime lastLoginDate;

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
