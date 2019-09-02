package com.perfect.bean.pojo.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zxh
 * @date 2019/8/30
 */
@Data
@Builder
@AllArgsConstructor
public class CheckResult implements Serializable {

    private static final long serialVersionUID = -3879117049651813655L;

    /** 返回消息：返回的消息 */
    private String message;

    /** 是否成功[true:成功;false:失败]，默认失败 */
    private boolean success;

    /** 返回数据 */
    private Object data ;
}
