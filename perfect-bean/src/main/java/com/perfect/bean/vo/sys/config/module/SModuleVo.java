package com.perfect.bean.vo.sys.config.module;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;

import com.perfect.bean.vo.condition.common.PageCondition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 模块：页面、task等
 * </p>
 *
 * @author zxh
 * @since 2019-09-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("s_module")
public class SModuleVo implements Serializable {

    private static final long serialVersionUID = -4793768968717402701L;

    private Long id;

    /**
     * 模块编号
     */
    private String code;

    /**
     * 模块类型
     */
    private String type;

    /**
     * 模块类型选择，页面上
     */
    private String [] types;

    /**
     * 名称
     */
    private String name;

    /**
     * 模版id：资源文件中获取
     */
    private Long templateId;

    /**
     * 描述
     */
    private String descr;

    /**
     * 是否删除
     */
    private Boolean isdel;

    /**
     * 资源类型
     */
    private String templateType;

    /**
     * 资源名称
     */
    private String templateName;

    /**
     * 资源描述
     */
    private String templateDescr;

    /**
     * json配置文件
     */
    private String templateContext;

    /**
     * 换页条件
     */
    private PageCondition pageCondition;

    private Long cId;

    private LocalDateTime cTime;

    private Long uId;

    private LocalDateTime uTime;

    /**
     * 数据版本，乐观锁使用
     */
    private Integer dbversion;


}
