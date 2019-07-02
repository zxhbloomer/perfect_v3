package com.zxh.bean.entity.oauth;

import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Blob;
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
 * @since 2019-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oauth_code")
public class OauthCodeEntity implements Serializable {

    private static final long serialVersionUID = 3405279390702545807L;

    @TableId("code")
    private String code;

    @TableField("authentication")
    private Blob authentication;


}
