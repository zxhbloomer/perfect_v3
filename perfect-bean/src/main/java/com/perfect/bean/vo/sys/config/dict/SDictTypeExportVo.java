package com.perfect.bean.vo.sys.config.dict;

import java.io.Serializable;

import com.perfect.common.annotation.Excel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 资源表导出Bean
 * </p>
 *
 * @author zxh
 * @since 2019-08-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "字典类型导出Bean", description = "字典类型导出Bean")
public class SDictTypeExportVo implements Serializable {

    private static final long serialVersionUID = -6942475112738825609L;
    private Long id;

    @Excel(name = "字典类型")
    private String code;

    @Excel(name = "字典名称")
    private String name;

    @Excel(name = "描述")
    private String descr;

    @Excel(name = "是否删除")
    private String isdel;

}
