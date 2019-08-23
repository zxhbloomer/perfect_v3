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
@ApiModel(value = "资源表导出Bean", description = "资源表导出Bean")
public class SDictDataExportVo implements Serializable {

    private static final long serialVersionUID = 6419636324818821669L;
    private Long id;

    /**
     * excel导入模板文件：10，静态配置文件：20，静态图片文件：30
     */
    @Excel(name = "资源类型")
    private String type;

    /**
     * 相对路径
     */
    @Excel(name = "相对路径")
    private String uri;

    /**
     * 文件系统的baseurl
     */
    @Excel(name = "baseurl")
    private String base;

    /**
     * 文件大小
     */
    @Excel(name = "文件大小")
    private Long size;

    /**
     * 文件扩展名
     */
    @Excel(name = "文件扩展名")
    private String extension;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String descr;

}
