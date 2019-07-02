package com.perfect.bean.entity.client.login;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2019-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("m_user")
public class MUserEntity implements Serializable {
    private static final long serialVersionUID = 1361286240767999313L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    /**
     * 系统用户=10,职员=20,客户=30,供应商=40,其他=50,认证管理员=60,审计管理员=70
     */
    @TableField("type")
    private String type;

    /**
     * 描述
     */
    @TableField("desc")
    private String desc;

    /**
     * 密码

     */
    @TableField("pwd")
    private String pwd;

    /**
     * 是否是已经删除

     */
    @TableField("delete")
    private Boolean delete;

    /**
     * 是否锁定

     */
    @TableField("locked")
    private Boolean locked;

    /**
     * 是否禁用

     */
    @TableField("forbidden")
    private Boolean forbidden;

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

    @TableField("locked_time")
    private LocalDateTime lockedTime;

    @TableField("is_biz_admin")
    private Boolean isBizAdmin;

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

    @TableField("home_telno")
    private String homeTelno;

    @TableField("office_telno")
    private String officeTelno;

    @TableField("cell telno")
    private String cellTelno;

    @TableField("c_id")
    private Long cId;

    @TableField("c_time")
    private LocalDateTime cTime;

    @TableField("u_id")
    private Long uId;

    @TableField("u_time")
    private LocalDateTime uTime;


}
