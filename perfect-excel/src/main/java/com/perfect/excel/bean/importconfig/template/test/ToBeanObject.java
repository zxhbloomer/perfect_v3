package com.perfect.excel.bean.importconfig.template.test;

import com.alibaba.fastjson.JSON;
import com.perfect.common.utils.JsonConvertUtil;
import com.perfect.excel.bean.importconfig.template.ExcelTemplate;

/**
 * @author zxh
 * @date 2019-08-07
 */
public class ToBeanObject {
    public static void main(String[] args) {
        String json = "{\"dataRows\":{\"dataCols\":[{\"convertor\":\"datetime\",\"index\":0,\"name\":\"type\"},{\"convertor\":\"date\",\"index\":\"1\",\"listValiDator\":[{\"validtorName\":\"required\"},{\"validtorName\":\"datetime\"}],\"name\":\"code\"},{\"index\":\"2\",\"name\":\"name\"},{\"index\":3,\"name\":\"descr\"},{\"index\":4,\"name\":\"simpleName\"}]},\"titleRows\":[{\"cols\":[{\"colSpan\":1,\"title\":\"角色类型\"},{\"colSpan\":1,\"title\":\"角色编码\"},{\"colSpan\":1,\"title\":\"角色名称\"},{\"colSpan\":1,\"title\":\"描述\"},{\"colSpan\":1,\"title\":\"简称\"}]}]}";
        ExcelTemplate et =  JSON.parseObject(json, ExcelTemplate.class);
        System.out.println("");
    }
}
