package com.perfect.bean.vo.sys.config.tenant;

import com.perfect.bean.vo.common.condition.PageCondition;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 租户主表
 * </p>
 *
 * @author zxh
 * @since 2019-10-02
 */
@Data
@NoArgsConstructor
@ApiModel(value = "租户信息", description = "租户信息")
public class STentantVo implements Serializable {

    private static final long serialVersionUID = 6408599988501466911L;

    private Long id;

    private Long parentid;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    private Boolean isenable;

    /**
     * 生效日期
     */
    private LocalDateTime enable_time;

    /**
     * 失效日期
     */
    private LocalDateTime disable_time;

    /**
     * 是否冻结
     */
    private Boolean isfreeze;

    /**
     * 是否叶子节点
     */
    private Boolean isleaf;

    /**
     * 级次
     */
    private Integer level;

    /**
     * 描述
     */
    private String descr;

    /**
     * 是否删除
     */
    private Boolean isdel;

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
