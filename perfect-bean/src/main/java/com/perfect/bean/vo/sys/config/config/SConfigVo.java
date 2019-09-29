package com.perfect.bean.vo.sys.config.config;

import com.baomidou.mybatisplus.annotation.*;
import com.perfect.bean.pojo.fs.UploadFileResultPojo;
import com.perfect.bean.vo.common.condition.PageCondition;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zxh
 * @date 2019/9/26
 */
@Data
@NoArgsConstructor
@ApiModel(value = "参数数据信息", description = "参数数据信息")
public class SConfigVo extends UploadFileResultPojo implements Serializable {
    private static final long serialVersionUID = -21039960705170821L;

    private Long id;

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数键名
     */
    private String config_key;

    /**
     * 参数键值
     */
    private String value;

    /**
     * 是否禁用(1:false-未启用,0:true-已启用)
     */
    private Boolean isenable;

    /**
     * 描述
     */
    private String descr;

    private Long c_id;

    private LocalDateTime c_time;

    private Long u_id;

    private LocalDateTime u_time;

    /**
     * 数据版本，乐观锁使用
     */
    private Integer dbversion;

    /**
     * 换页条件
     */
    private PageCondition pageCondition;
}
