package com.perfect.bean.entity.security;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("persistent_logins")
public class PersistentLoginsEntity implements Serializable {

    private static final long serialVersionUID = 1436212710530365793L;

    @TableField("username")
    private String username;

    @TableId("series")
    private String series;

    @TableField("token")
    private String token;

    @TableField("last_used")
    private LocalDateTime lastUsed;


}
