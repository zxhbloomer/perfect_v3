package com.perfect.bean.vo.user.info;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "用户基本信息", description = "用户基本信息vo_bean")
public class UserInfoVo implements Serializable {

    private static final long serialVersionUID = 574627344179000681L;

    private String token;

    private String[] roles;

    private String introduction;

    private String avatar;

    private String name;
}
