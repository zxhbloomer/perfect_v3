package com.perfect.bean.vo.sys.rabc;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "角色返回信息", description = "角色返回vo_bean")
public class SysRoleVo implements Serializable {

    private static final long serialVersionUID = 2443084812232177470L;

    private Long id;

    /**
     * 角色类型
     */
    private String type;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 描述
     */
    private String descr;

    /**
     * 简称
     */
    private String simpleName;

    /**
     * 租户代码
     */
    private String corpCode;

    /**
     * 租户名称
     */
    private String corpName;
}
