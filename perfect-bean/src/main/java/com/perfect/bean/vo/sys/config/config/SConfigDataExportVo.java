package com.perfect.bean.vo.sys.config.config;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.perfect.bean.pojo.fs.UploadFileResultPojo;
import com.perfect.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zxh
 * @date 2019/9/26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "参数配置表导出Bean", description = "参数配置表导出Bean")
public class SConfigDataExportVo implements Serializable {

    private static final long serialVersionUID = -21039960705170821L;

    private Long id;

    /**
     * 参数名称
     */
    @Excel(name = "参数名称")
    private String name;

    /**
     * 参数键名
     */
    @Excel(name = "参数键名")
    private String config_key;

    /**
     * 参数键值
     */
    @Excel(name = "参数键值")
    private String value;

    /**
     * 是否禁用(1:false-未启用,0:true-已启用)
     */
    @Excel(name = "是否禁用")
    private Boolean isenable;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String descr;

    private Long c_id;

    private LocalDateTime c_time;

    private Long u_id;

    private LocalDateTime u_time;

    /**
     * 数据版本，乐观锁使用
     */
    private Integer dbversion;
}
