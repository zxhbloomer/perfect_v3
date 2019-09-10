package com.perfect.bean.vo.sys.module;

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
     * 类型
     */
    private String type;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String descr;

    /**
     * 是否删除
     */
    private Boolean isdel;

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
