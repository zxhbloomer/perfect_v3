package com.perfect.bean.vo.sys.config.dict;

import java.io.Serializable;

import com.perfect.bean.pojo.fs.UploadFileResultPojo;
import com.perfect.bean.vo.condition.common.PageCondition;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangxh
 */
@Data
@NoArgsConstructor
@ApiModel(value = "字典类型信息", description = "字典类型vo_bean")
public class SDictTypeVo extends UploadFileResultPojo implements Serializable {

    private static final long serialVersionUID = 8149295048471235932L;
    private Long id;

    /**
     * 角色类型
     */
    private String type;

    /**
     * 角色编码
     */
    private String [] code;

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

    /**
     * 换页条件
     */
    private PageCondition pageCondition;

    /**
     * 是否删除
     */
    private Boolean isdel;
}
