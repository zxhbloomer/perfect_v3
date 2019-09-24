package com.perfect.bean.vo.common.component;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zxh
 * @date 2019/9/24
 */
@Data
@NoArgsConstructor
@ApiModel(value = "下拉选项的数据", description = "下拉选项的数据")
public class NameAndValueVo implements Serializable {
    private static final long serialVersionUID = 6697222826158984527L;

    /**
     * 显示的数据
     */
    private String name;

    /**
     * 对应的id
     */
    private String value;

    private String dict_type_code;
    private Long dict_data_id;
}
