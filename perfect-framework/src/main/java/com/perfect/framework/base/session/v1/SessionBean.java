package com.perfect.framework.base.session.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class SessionBean extends JdkSerializationRedisSerializer implements Serializable {

    private static final long serialVersionUID = 6503859853529665708L;

    private String sessionId;

    private String sessionUuid; // 会话ID

    private Long accountId; // 帐号ID

    private String appKey; // 应用标识

    private Long tenantId; // 帐号所属租户ID

    private Boolean tenantAdmin; // 帐号是否租户的管理员

    private Set<String> operationSet = new HashSet<String>(); // 帐号所拥有的权限

    private String extra;

}
