package com.perfect.bean.entity.oauth;

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
@TableName("oauth_refresh_token")
public class OauthRefreshTokenEntity implements Serializable {

    private static final long serialVersionUID = 6716428217652502751L;

    @TableId("token_id")
    private String tokenId;

    @TableField("token")
    private Blob token;

    @TableField("authentication")
    private Blob authentication;


}
