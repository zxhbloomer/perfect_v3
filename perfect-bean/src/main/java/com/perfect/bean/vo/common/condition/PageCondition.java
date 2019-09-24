package com.perfect.bean.vo.common.condition;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ApiModel(value = "分页条件bean", description = "分页条件bean")
public class PageCondition implements Serializable {

    private static final long serialVersionUID = 7808161515093912080L;

    /** 当前页 */
    private long current;
    /**  每页显示条数*/
    private long size;
    /**  排序*/
    private String sort;
}
