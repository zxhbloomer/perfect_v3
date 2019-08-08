package com.perfect.excel.bean.importconfig.template.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.perfect.excel.bean.importconfig.template.ExcelTemplate;

/**
 * 测试bean
 *
 * @author zxh
 * @date 2019年 08月06日 21:36:51
 */
public class ToJsonString {
    public static void main(String[] args) {
        BeanSetting dt = new BeanSetting();
        ExcelTemplate et = new ExcelTemplate();
        // 设置表头
        et.setTitleRows(dt.getTitleRows());
        et.setDataRow(dt.getDataRow());
        String jsonString = JSON.toJSONString(et);
        System.out.println(jsonString);
    }
}
