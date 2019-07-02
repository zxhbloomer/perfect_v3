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
@TableName("oauth_access_token")
public class OauthAccessTokenEntity implements Serializable {

    private static final long serialVersionUID = -6248586560188328858L;

    @TableField("token_id")
    private String tokenId;

    @TableField("token")
    private Blob token;

    @TableId("authentication_id")
    private String authenticationId;

    @TableField("user_name")
    private String userName;

    @TableField("client_id")
    private String clientId;

    @TableField("authentication")
    private Blob authentication;

    @TableField("refresh_token")
    private String refreshToken;


}
