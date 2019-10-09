package com.perfect.bean.vo.sys.config.tenant;

import com.perfect.bean.vo.common.component.TreeNode;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 生成租户树数据的接收类
 *
 * @author zxh
 * @date 2019年 10月03日 11:55:24
 */
@Data
public class STentantTreeVo extends TreeNode implements Serializable {

    private static final long serialVersionUID = -7928187114619523854L;

    /**
     * 编码
     */
    private String code;

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
     * 描述
     */
    private String descr;

    /**
     * 树节点名称：按深度显示名称 xx>...>xx
     */
    private String depth_name;

    /**
     * 系统编码：如：第一层：0001
     *              第二层：00010001
     */
    private String serial_no;

    /**
     * 父亲节点编码
     */
    private String parent_code;

    /**
     * 父亲节点系统编码
     */
    private String parent_serial_no;

    /**
     * 级联控件使用，父节点id
     */
    private Long value;

}
