package com.perfect.bean.vo.sys.config.tenant;

import com.perfect.bean.vo.common.tree.IDataTree;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 生成租户树数据的接收类
 *
 * @author zxh
 * @date 2019年 10月03日 11:55:24
 */
@Data
public class STentantTreeVo_ extends IDataTree<STentantTreeVo_, STentantVo> implements Serializable {

    private static final long serialVersionUID = -7928187114619523854L;

    STentantTreeVo_(){
    }
    STentantTreeVo_(Long id, String lable, List<STentantTreeVo_> children, STentantVo data){
        super.setId(id);
        super.setLable(lable);
        super.setChildren(children);
        super.setData(data);
    }

}
