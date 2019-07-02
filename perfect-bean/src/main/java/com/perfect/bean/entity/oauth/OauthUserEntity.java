package com.perfect.bean.entity.oauth;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2019-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oauth_user")
public class OauthUserEntity implements Serializable {

    private static final long serialVersionUID = -7784407781764026251L;

    @TableId("id")
    private Long id;

    @TableField("createTime")
    private LocalDateTime createTime;

    @TableField("creatorid")
    private Long creatorid;

    @TableField("eectiveDate")
    private LocalDateTime eectiveDate;

    @TableField("errCount")
    private Integer errCount;

    @TableField("forbidden")
    private Integer forbidden;

    @TableField("groupid")
    private Long groupid;

    @TableField("homePhone")
    private String homePhone;

    @TableField("invalidationDate")
    private LocalDateTime invalidationDate;

    @TableField("isDelete")
    private Integer isDelete;

    @TableField("isLocked")
    private Integer isLocked;

    @TableField("lockedTime")
    private LocalDateTime lockedTime;

    @TableField("name")
    private String name;

    @TableField("oicePhone")
    private String oicePhone;

    @TableField("password")
    private String password;

    @TableField("pweectiveDate")
    private LocalDateTime pweectiveDate;

    @TableField("remark")
    private String remark;

    @TableField("tell")
    private String tell;

    @TableField("type")
    private String type;

    @TableField("updateid")
    private Long updateid;

    @TableField("updatetime")
    private LocalDateTime updatetime;


}
