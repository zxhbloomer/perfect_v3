package com.perfect.bean.vo.sys.rabc.role;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.perfect.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.*;
import com.perfect.bean.entity.base.entity.v1.BaseEntity;

/**
 * <p>
 * 角色导出Bean
 * </p>
 *
 * @author zxh
 * @since 2019-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "角色导出Bean", description = "角色导出Bean")
public class SRoleExportVo implements Serializable {

    private static final long serialVersionUID = -7449124258332853610L;

    private Long id;

    /**
     * 角色类型
     */
    @Excel(name = "角色类型")
    private String type;

    /**
     * 角色编码
     */
    @Excel(name = "角色编码")
    private String code;

    /**
     * 角色名称
     */
    @Excel(name = "角色名称")
    private String name;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String descr;

    /**
     * 简称
     */
    @Excel(name = "简称")
    private String simpleName;

}
